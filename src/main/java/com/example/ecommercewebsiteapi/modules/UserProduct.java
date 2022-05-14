package com.example.ecommercewebsiteapi.modules;

public class UserProduct {
    int id;
    String title;
    int price;
    int quantity;
    String photoUrl;
    String details;
    String category;
    int orderedQuantity;

    public UserProduct(int id, String title, int price, int quantity, String photoUrl, String details, String category, int orderedQuantity) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.quantity = quantity;
        this.photoUrl = photoUrl;
        this.details = details;
        this.category = category;
        this.orderedQuantity = orderedQuantity;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getOrderedQuantity() {
        return orderedQuantity;
    }

    public void setOrderedQuantity(int orderedQuantity) {
        this.orderedQuantity = orderedQuantity;
    }
}
