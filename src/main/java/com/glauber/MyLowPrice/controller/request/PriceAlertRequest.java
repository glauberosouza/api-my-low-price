package com.glauber.MyLowPrice.controller.request;

import com.glauber.MyLowPrice.domain.entities.PriceAlert;
import com.glauber.MyLowPrice.domain.entities.Product;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriceAlertRequest {
    @NotBlank(message = "O campo nome deve ser preenchido corretamente!")
    private String name;
    @NotBlank(message = "O campo product name deve ser preenchido corretamente!")
    private String productName;
    @Min(value = 1, message = "Range de preço deve ser maior que 1")
    private double priceRange;
    @NotBlank(message = "O campo email deve ser preenchido corretamente!")
    private String email;

    public static PriceAlert toPriceAlertEntity(PriceAlertRequest priceAlertRequest) {
        var priceAlert = new PriceAlert();
        priceAlert.setName(priceAlertRequest.getName());
        priceAlert.setProductName(priceAlertRequest.getProductName());
        priceAlert.setPriceRange(priceAlertRequest.getPriceRange());
        priceAlert.setEmail(priceAlertRequest.getEmail());
        return priceAlert;
    }
}
