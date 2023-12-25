package com.glauber.MyLowPrice.domain.entities;

import com.glauber.MyLowPrice.controller.response.PriceAlertResponse;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Entity
@Table(name = "price_alert")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceAlert implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column
    private String name;
    @Column(name = "product_name")
    private String productName;

    @Column(name = "price_range")
    private double priceRange;

    @Column
    private String email;

    public static PriceAlertResponse toPriceAlertResponse(PriceAlert priceAlert) {
        var priceAlertResponse = new PriceAlertResponse();
        priceAlertResponse.setId(priceAlert.getId());
        priceAlertResponse.setName(priceAlert.getName());
        priceAlertResponse.setProductName(priceAlert.getProductName());
        priceAlertResponse.setPriceRange(priceAlert.getPriceRange());
        priceAlertResponse.setEmail(priceAlert.getEmail());
        return priceAlertResponse;
    }
}
