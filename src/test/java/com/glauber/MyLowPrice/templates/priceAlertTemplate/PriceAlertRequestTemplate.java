package com.glauber.MyLowPrice.templates.priceAlertTemplate;

import com.glauber.MyLowPrice.templates.productTemplate.ProductRequest;

import java.math.BigDecimal;

public class PriceAlertRequestTemplate {
    public static PriceAlertRequest creation() {
        return new PriceAlertRequest(
                "Glauber",
                "Xbox",
                100.0,
                "glauber@gmail.com"
        );
    }
}
