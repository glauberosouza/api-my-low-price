package com.glauber.MyLowPrice.controller.request;

import com.glauber.MyLowPrice.domain.entities.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
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
    @NotBlank(message = "O campo nome deve ser preenchido corretamente!")
    private String name;
    @NotBlank(message = "O campo link deve ser preenchido corretamente!")
    private String link;
    @Min(value = 1, message = "Preço preenchido deve ser maior que 1")
    private BigDecimal price;

    public static Product toProductEntity(ProductRequest productRequest) {
        var product = new Product();
        product.setName(productRequest.getName());
        product.setLink(productRequest.getLink());
        product.setPrice(productRequest.getPrice());
        return product;
    }
}