package com.glauber.MyLowPrice.templates;

import java.math.BigDecimal;

public class ProductRequest {
    private String name;
    private String link;
    private BigDecimal price;

    public ProductRequest() {

    }

    public ProductRequest(String name, String link, BigDecimal price) {
        this.name = name;
        this.link = link;
        this.price = price;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }
}
