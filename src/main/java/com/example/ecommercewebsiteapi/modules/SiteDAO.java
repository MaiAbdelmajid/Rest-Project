package com.example.ecommercewebsiteapi.modules;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SiteDAO {
    public static final String DB_URL = "jdbc:postgresql://localhost:5432/";
    private String dbname = "trialJsp";
    private String user = "postgres";
    private String pass = "1502654";

    private Connection con;
    public static SiteDAO instanceData;
    PreparedStatement stmt;

    public SiteDAO(String dbname, String user, String pass) throws SQLException {
        this.connect(dbname, user, pass);
    }

    private void connect(String dbname, String user, String pass) throws SQLException {
        this.con = DriverManager.getConnection(DB_URL + dbname, user, pass);
        instanceData = this;
    }

    public int addToCart(Cart cart) throws SQLException {
        stmt = this.con.prepareStatement("insert into cart(productid,userid,quantity) values(?,?,?)");
        stmt.setInt(1, cart.getProductId());
        stmt.setInt(2, cart.getUserId());
        stmt.setInt(3, cart.getQuantity());

        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();

        if (rs != null) {
            return 1;
        } else {
            return -1;
        }
    }

    public int deleteFromCart(int productid, int userid) throws SQLException {
        System.out.println("prod id "+productid);
        System.out.println("user id "+userid);
        stmt = this.con.prepareStatement("delete from cart where productid = ? and userid = ?" );
        stmt.setInt(1,productid);
        stmt.setInt(2,userid);

        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();
        if (rs != null) {
            return 1;
        } else {
            return -1;
        }
    }

    public int delete(int id) throws SQLException {
        System.out.print(id);
        stmt = this.con.prepareStatement("delete from product where id = ?" );
        stmt.setInt(1,id);
        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();

        if (rs.next()) {
            return 1;
        }else{
            return 0;
        }
    }

    public int AddProduct(String title, int Price, int Quantity, String photoUrl, String details, String category) throws SQLException {
        stmt = this.con.prepareStatement("insert into product(title,price,quantity,photo,details,category) values(?,?,?,?,?,?)");
        stmt.setString(1, title);
        stmt.setInt(2, Price);
        stmt.setInt(3, Quantity);
        stmt.setString(4, photoUrl);
        stmt.setString(5, details);
        stmt.setString(6, category);

        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();

        if (rs != null) {
            return 1;
        } else {
            return -1;
        }
    }

    public List<Product> getProducts() throws SQLException {
        stmt = this.con.prepareStatement("select * from product");
        ResultSet rs = stmt.executeQuery();
        List<Product> products = new ArrayList<>();

        while (rs.next()) {
            products.add(new Product(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getInt("price"),
                    rs.getInt("quantity"),
                    rs.getString("photo"),
                    rs.getString("details"),
                    rs.getString("category")));
        }

        System.out.println("in get product");
        return products;
    }

    public List<Cart> getCart() throws SQLException {
        stmt = this.con.prepareStatement("select * from cart");
        ResultSet rs = stmt.executeQuery();
        List<Cart> products = new ArrayList<>();

        while (rs.next()) {
            products.add(new Cart(
                    rs.getInt("productid"),
                    rs.getInt("userid"),
                    rs.getInt("quantity")));
        }
        System.out.println("in get cart");

        return products;
    }

    public int checkSignUp(boolean admin, String name, Date birthday, String password, int phonenumber, String job, String email, int creditlimit, String address, String interests) {
        try {
//            java.sql.Date currDate = new java.sql.Date(Calendar.getInstance().getTime().getTime());
            stmt = this.con.prepareStatement("insert into users (admin,name,birthday,password,phonenumber,job,email,creditlimit,address,interests) values(?,?,?,?,?,?,?,?,?,?)");
            stmt.setBoolean(1, admin);
            stmt.setString(2, name);
            stmt.setDate(3, birthday);
            stmt.setString(4, password);
            stmt.setInt(5, phonenumber);
            stmt.setString(6, job);
            stmt.setString(7, email);
            stmt.setInt(8, creditlimit);
            stmt.setString(9, address);
            stmt.setString(10, interests);
            stmt.executeUpdate();

            return 1;
        } catch (SQLException var6) {
            return -1;
        }
    }

    public User checkSignIn(String email, String password) {
        try {
            PreparedStatement stmt = con.prepareStatement("select * from users where email = ?");
            stmt.setString(1, email);
            ResultSet rs = stmt.executeQuery();
            User user = null;
            if (rs != null) {
                rs.next();
                user = new User(rs.getInt("id"),
                        rs.getBoolean("admin"),
                        rs.getString("name"),
                        rs.getString("birthday"),
                        rs.getString("password"),
                        rs.getInt("phonenumber"),
                        rs.getString("job"),
                        rs.getString("email"),
                        rs.getInt("creditlimit"),
                        rs.getString("address"),
                        rs.getString("interests"));

                System.out.println(email + " " + user.password);
                //return user;
            }

            System.out.println("entered pass "+password);
            System.out.println("user id "+user.getId());

            if (user != null && password.equals(user.password)) {
                System.out.println("yes ");
                return user;
                //return "Logged in successfully";
            } else if (user==null ||!password.equals(user.password)) {
                return null;
//                return "you have already signed in from another device";
            } else {
                return null;
//                return "Email or Password is not correct";
            }
        } catch (SQLException ex) {
//            return "Email or Password is not correct";
            return null;
        }
    }

    public String checkout(User user, List<Product> userProducts, List<Cart> userCart) throws SQLException {
        int totalPrice = 0;

        List<UserProduct> userProduct = new ArrayList<>();
        for (Cart cart : userCart) {
            for (Product product : userProducts) {
                if (user.getId() == cart.getUserId() && cart.getProductId() == product.getId()) { //check for the ownership of the cat for that user
                    if (cart.quantity > product.quantity) {
                        return "ordered product is out of stock";
                    }
                    userProduct.add(
                            new UserProduct(product.getId()
                                    , product.getTitle(), product.getPrice()
                                    , product.getQuantity(), product.getPhotoUrl()
                                    , product.getDetails(), product.getCategory()
                                    , cart.getQuantity()));

                    totalPrice += product.getPrice()*cart.getQuantity();
                }
            }
        }

        if (totalPrice > user.getCreditLimit()) {
            return "you dont have enough credit";
        }

        int value = master(user, totalPrice, userProducts, userProduct);
        if (value != 1) {
            return "error while charging user";
        }
        return "success";
        //check for quantity > ordered quantity or not
        //check for total price if smaller than user credit
    }

    private int master(User user, int totalPrice, List<Product> products, List<UserProduct> userProducts) throws SQLException {
        StringBuilder query = new StringBuilder("BEGIN;" +
                "update users set creditlimit= ? where id= ?;");
        for (Product product : products) {
            query.append("delete from cart where productid =")
                    .append(product.getId())
                    .append(";");
        }

        for (UserProduct product : userProducts) {
            query.append("update product set quantity = ")
                    .append(product.getQuantity() - product.getOrderedQuantity())
                    .append(" where id =")
                    .append(product.getId())
                    .append(";");
        }
        query.append("COMMIT;");

        System.out.println(query.toString());
        PreparedStatement statement = con.prepareStatement(query.toString());
        statement.setInt(1, user.getCreditLimit() - totalPrice);
        statement.setInt(2, user.getId());
        statement.executeUpdate();

        //decrease credit from user
        //remove products from user cart
        //decease product quantity

        return 1;
    }

    public List<Product> getCategory(String category) {
        //this function get a list of products according to specific category
        //implement function here

        return null;
    }

    public List<Product> getLatest() {
        //this function gets the latest 3 products
        //implement function here

        return null;
    }

    public List<Product> getProduct(int id) throws SQLException {
        stmt = this.con.prepareStatement("select * from product where id = ?");
        stmt.setInt(1,id);
        ResultSet rs = stmt.executeQuery();
        List<Product> product = new ArrayList<>();


        while (rs.next()) {
            product.add(new Product(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getInt("price"),
                    rs.getInt("quantity"),
                    rs.getString("photo"),
                    rs.getString("details"),
                    rs.getString("category")));
        }

        System.out.println(product.size());

        System.out.println("in get product");
        return product;
    }

    public List<Product> getProduct(String title) throws SQLException {
        stmt = this.con.prepareStatement("select * from product where title like ?");
        stmt.setString(1,"%" + title + "%");
        ResultSet rs = stmt.executeQuery();
        List<Product> product = new ArrayList<>();


        while (rs.next()) {
            product.add(new Product(
                    rs.getInt("id"),
                    rs.getString("title"),
                    rs.getInt("price"),
                    rs.getInt("quantity"),
                    rs.getString("photo"),
                    rs.getString("details"),
                    rs.getString("category")));
        }

        System.out.println(product.size());

        System.out.println("in get product");
        return product;
    }

    public int editProduct(int price, int quantity, int id) throws SQLException {
        stmt = this.con.prepareStatement("update product set price = ?,quantity=? where id = ?;");
        stmt.setInt(1, price);
        stmt.setInt(2, quantity);
        stmt.setInt(3, id);

        stmt.executeUpdate();
        ResultSet rs = stmt.getGeneratedKeys();

        if (rs != null) {
            return 1;
        } else {
            return -1;
        }
    }
}
