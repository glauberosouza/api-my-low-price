package com.glauber.MyLowPrice.controller.request;

import com.glauber.MyLowPrice.domain.entities.Product;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ProductRequest {
    private String name;
    private String link;
    private BigDecimal price;

    public static Product toProductEntity(ProductRequest productRequest) {
        var product = new Product();
        product.setName(productRequest.getName());
        product.setLink(productRequest.getLink());
        product.setPrice(productRequest.getPrice());
        return product;
    }
}