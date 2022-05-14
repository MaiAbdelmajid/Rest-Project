package com.example.ecommercewebsiteapi;

import com.example.ecommercewebsiteapi.modules.Cart;
import com.example.ecommercewebsiteapi.modules.Product;
import com.example.ecommercewebsiteapi.modules.SiteDAO;
import com.example.ecommercewebsiteapi.modules.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import jakarta.ws.rs.*;
import jakarta.ws.rs.core.MediaType;

import java.sql.Date;
import java.sql.SQLException;
import java.util.List;

@Path("/eShopper")
public class HelloResource {
    public void DBConnect() throws ClassNotFoundException, SQLException, InstantiationException, IllegalAccessException {
        Class.forName("org.postgresql.Driver").newInstance();
        new SiteDAO("trialJsp","postgres","1502654");
    }

    @GET
    @Path("/getProducts")@Produces("application/json")
    public String getProducts() throws SQLException, JsonProcessingException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        DBConnect();
        List<Product> employee = SiteDAO.instanceData.getProducts();
        if (employee != null) {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(employee);
            System.out.println(jsonString);
            return jsonString;
        } else {
            return "null";
        }
    }

    @GET
    @Path("/getProduct/{id}")@Produces("application/json")
    public String getProduct(@PathParam("id") String body) throws SQLException, JsonProcessingException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        DBConnect();
        List<Product> employee = SiteDAO.instanceData.getProduct(Integer.parseInt(body));
        if (employee != null) {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(employee);
            System.out.println(jsonString);
            return jsonString;
        } else {
            return "null";
        }
    }

    @GET
    @Path("/getProductWithTitle/{title}")@Produces("application/json")
    public String getProductWithTitle(@PathParam("title") String body) throws SQLException, JsonProcessingException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        DBConnect();
        List<Product> employee = SiteDAO.instanceData.getProduct(body);
        if (employee != null) {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(employee);
            System.out.println(jsonString);
            return jsonString;
        } else {
            return "null";
        }
    }

    @GET
    @Path("/getCart")@Produces("application/json")
    public String getCart() throws SQLException, JsonProcessingException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        DBConnect();
        List<Cart> employee = SiteDAO.instanceData.getCart();
        if (employee != null) {
            ObjectMapper mapper = new ObjectMapper();
            String jsonString = mapper.writeValueAsString(employee);
            System.out.println(jsonString);
            return jsonString;
        } else {
            return "null";
        }
    }

    @POST
    @Path("/addToCart")
    @Consumes(MediaType.APPLICATION_JSON)@Produces(MediaType.TEXT_PLAIN)
    public String addToCart(String cart) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        DBConnect();
        System.out.println("this is the request "+cart);
        Gson gson = new Gson();
        Cart emp = gson.fromJson(cart, Cart.class);
        int employee = SiteDAO.instanceData.addToCart(emp);
        if (employee == 1) {
            return "The data added successfully";
        } else {
            return "There is error occurred while adding user";
        }
    }

    @POST
    @Path("/addProduct")
    @Consumes(MediaType.APPLICATION_JSON)@Produces(MediaType.TEXT_PLAIN)
    public String addProduct(String body) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        DBConnect();
        System.out.println("this is the request "+body);
        Gson gson = new Gson();
        Product emp = gson.fromJson(body, Product.class);
        int employee = SiteDAO.instanceData.AddProduct(emp.getTitle(),emp.getPrice(),emp.getQuantity(),emp.getPhotoUrl(),emp.getDetails(),emp.getCategory());
        if (employee == 1) {
            return "The data added successfully";
        } else {
            return "There is error occurred while adding user";
        }
    }

    @POST
    @Path("/checkSignUp")
    @Consumes(MediaType.APPLICATION_JSON)@Produces(MediaType.TEXT_PLAIN)
    public String checkSignUp(String body) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        DBConnect();
        System.out.println("this is the request "+body);
        Gson gson = new Gson();
        User emp = gson.fromJson(body, User.class);
        int employee = SiteDAO.instanceData.checkSignUp(emp.isAdmin(),emp.getName(), Date.valueOf(emp.getBirthDate()),emp.getPassword(),emp.getPhoneNumber(),emp.getJop(),emp.getEmail(),emp.getCreditLimit(),emp.getAddress(),emp.getInterests());
        if (employee == 1) {
            return "The data added successfully";
        } else {
            return "There is error occurred while adding user";
        }
    }

    @POST
    @Path("/checkSignIn")
    @Consumes(MediaType.APPLICATION_JSON)@Produces(MediaType.APPLICATION_JSON)
    public String checkSignIn(String body) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException {
        DBConnect();
        System.out.println("this is the request "+body);
        JsonObject jobj = new Gson().fromJson(body, JsonObject.class);
        String email = jobj.get("email").toString().replaceAll("\"", "");
        String password = jobj.get("password").toString().replaceAll("\"", "");

        User employee = SiteDAO.instanceData.checkSignIn(email,password);
        if (employee !=null) {
            ObjectMapper mapper = new ObjectMapper();
            return mapper.writeValueAsString(employee);
        } else {
            return "null";
        }
    }

    @POST
    @Path("/editProduct")
    @Consumes(MediaType.APPLICATION_JSON)@Produces(MediaType.TEXT_PLAIN)
    public String editProduct(String body) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException {
        DBConnect();
        System.out.println("this is the request "+body);
        JsonObject jobj = new Gson().fromJson(body, JsonObject.class);
        String price = jobj.get("price").toString();
        String quantity = jobj.get("quantity").toString();
        String id = jobj.get("id").toString();
        int employee = SiteDAO.instanceData.editProduct(Integer.parseInt(price),Integer.parseInt(quantity),Integer.parseInt(id));
        if (employee == 1) {
            return "product edited";
        } else {
            return "null";
        }
    }

    @POST
    @Path("/checkout")
    @Consumes(MediaType.APPLICATION_JSON)@Produces(MediaType.TEXT_PLAIN)
    public String checkout(String body) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException, JsonProcessingException {
        DBConnect();
        System.out.println("this is the request "+body);
        JsonObject jobj = new Gson().fromJson(body, JsonObject.class);
        String user = jobj.get("user").toString();
        Gson gson = new Gson();
        User emp = gson.fromJson(user, User.class);
        return SiteDAO.instanceData.checkout(emp,SiteDAO.instanceData.getProducts(),SiteDAO.instanceData.getCart());
    }

    @DELETE
    @Path("/deleteFromCart")
    @Consumes(MediaType.APPLICATION_JSON) @Produces(MediaType.TEXT_PLAIN)
    public String deleteFromCart(String body) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        DBConnect();
        System.out.println("json: "+body);
        JsonObject jobj = new Gson().fromJson(body, JsonObject.class);
        String productid = jobj.get("productid").toString();
        String userid = jobj.get("userid").toString();
        int employee = SiteDAO.instanceData.deleteFromCart(Integer.parseInt(productid),Integer.parseInt(userid));
        if (employee == 1) {
            return "The data deleted successfully";
        } else {
            return "There is error occurred while deleting user";
        }
    }

    /*@POST
    @Path("/post")
    @Consumes(MediaType.APPLICATION_JSON)@Produces(MediaType.TEXT_PLAIN)
    public String postToDB(String id) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        DBConnect();
        System.out.println("this is the request "+id);
        Gson gson = new Gson();
        Employee emp = gson.fromJson(id, Employee.class);
        System.out.println("this is the name "+emp.getName()+emp.getDepartmentId());
        int employee = DAO.instanceData.addEmployee(emp);
        if (employee == 1) {
            return "The data added successfully";
        } else {
            return "There is error occurred while adding user";
        }
    }

    @PUT
    @Path("/put")
    @Consumes("application/json") @Produces("application/json")
    public String putToDB(String id) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        DBConnect();
        System.out.println("this is the request "+id);
        Gson gson = new Gson();
        Employee emp = gson.fromJson(id, Employee.class);
        System.out.println("this is the name "+emp.getName()+emp.getDepartmentId());
        int employee = DAO.instanceData.editEmployee(emp);
        if (employee == 1) {
            return "The data added successfully";
        } else {
            return "There is error occurred while adding user";
        }
    }


    @PATCH
    @Path("/patch")
    @Consumes("application/json") @Produces("application/json")
    public String patchToDB(String id) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        DBConnect();
        Gson gson = new Gson();
        Employee emp = gson.fromJson(id, Employee.class);
        int employee = DAO.instanceData.editEmployee(emp);
        if (employee == 1) {
            return "The data edited successfully";
        } else {
            return "There is error occurred while editing user";
        }
    }


    @DELETE
    @Path("/delete")
    @Consumes("application/json") @Produces("application/json")
    public String deleteToDB(String id) throws SQLException, ClassNotFoundException, InstantiationException, IllegalAccessException {
        DBConnect();
        Gson gson = new Gson();
        Employee emp = gson.fromJson(id, Employee.class);
        int employee = DAO.instanceData.deleteEmployee(emp);
        if (employee == 1) {
            return "The data deleted successfully";
        } else {
            return "There is error occurred while deleting user";
        }
    }*/
}