package com.example.ecommercewebsiteapi.modules;

public class Cart{
    int productId;
    int userId;
    int quantity;

    public Cart(int productId, int userId, int quantity) {
        this.productId = productId;
        this.userId = userId;
        this.quantity = quantity;
    }

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
