package com.glauber.MyLowPrice.templates;

import java.math.BigDecimal;
import java.time.LocalDate;

public class ProductRequestTemplate {
    public static ProductRequest creation() {
        return new ProductRequest(
                "Xbox",
                "@Xbox",
                BigDecimal.valueOf(2000.00)
        );
    }
}
