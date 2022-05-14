package com.example.ecommercewebsiteapi.modules;

public class User {
    int id;
    boolean admin;
    String name;
    String birthDate;
    String password;
    int phoneNumber;
    String jop;
    String email;
    int creditLimit;
    String address;
    String interests;
    boolean status;

    public User(int id, boolean admin, String name, String birthDate, String password, int phoneNumber, String jop, String email, int creditLimit, String address, String interests) {
        this.id = id;
        this.admin = admin;
        this.name = name;
        this.birthDate = birthDate;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.jop = jop;
        this.email = email;
        this.creditLimit = creditLimit;
        this.address = address;
        this.interests = interests;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(String birthDate) {
        this.birthDate = birthDate;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public int getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(int phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getJop() {
        return jop;
    }

    public void setJop(String jop) {
        this.jop = jop;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCreditLimit() {
        return creditLimit;
    }

    public void setCreditLimit(int creditLimit) {
        this.creditLimit = creditLimit;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getInterests() {
        return interests;
    }

    public void setInterests(String interests) {
        this.interests = interests;
    }
}
