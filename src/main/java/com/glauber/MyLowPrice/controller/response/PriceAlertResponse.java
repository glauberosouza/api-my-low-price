package com.glauber.MyLowPrice.controller.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceAlertResponse {
    private Long id;
    private String name;
    private String productName;
    private double priceRange;
    private String email;
}
