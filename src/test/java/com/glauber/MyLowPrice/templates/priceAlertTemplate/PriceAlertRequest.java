package com.glauber.MyLowPrice.templates.priceAlertTemplate;

public class PriceAlertRequest {
    private String name;
    private String productName;
    private double priceRange;
    private String email;


    public PriceAlertRequest(String name, String productName, double priceRange, String email) {
        this.name = name;
        this.productName = productName;
        this.priceRange = priceRange;
        this.email = email;
    }

    public boolean isPriceAlertTriggered(double desiredProductPrice) {
        return desiredProductPrice <= priceRange;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public double getPriceRange() {
        return priceRange;
    }

    public void setPriceRange(double priceRange) {
        this.priceRange = priceRange;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
