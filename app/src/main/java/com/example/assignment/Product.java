package com.example.assignment;

/*Abhishek Kumar Yadava*/

public class Product {

    private int id;
    private String name;
    private String description;
    private String regularPrice;
    private String salePrice;
    private byte[] productPhoto;

    public Product(int id, String name, String description, String regularPrice, String salePrice, byte[] productPhoto) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.regularPrice = regularPrice;
        this.salePrice = salePrice;
        this.productPhoto = productPhoto;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getRegularPrice() {
        return regularPrice;
    }

    public void setRegularPrice(String regularPrice) {
        this.regularPrice = regularPrice;
    }

    public String getSalePrice() {
        return salePrice;
    }

    public void setSalePrice(String salePrice) {
        this.salePrice = salePrice;
    }

    public byte[] getProductPhoto() {
        return productPhoto;
    }

    public void setProductPhoto(byte[] productPhoto) {
        this.productPhoto = productPhoto;
    }


}