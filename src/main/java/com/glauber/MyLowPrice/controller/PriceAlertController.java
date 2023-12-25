package com.glauber.MyLowPrice.controller;

import com.glauber.MyLowPrice.controller.request.PriceAlertRequest;
import com.glauber.MyLowPrice.controller.response.PriceAlertResponse;
import com.glauber.MyLowPrice.domain.entities.PriceAlert;
import com.glauber.MyLowPrice.service.PriceAlertService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alerts")
public class PriceAlertController {
    private final PriceAlertService priceAlertService;

    @Autowired
    public PriceAlertController(PriceAlertService priceAlertService) {
        this.priceAlertService = priceAlertService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid PriceAlertRequest priceAlertRequest) {
        var priceAlertEntity = PriceAlertRequest.toPriceAlertEntity(priceAlertRequest);
        priceAlertService.save(priceAlertEntity);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/page/{number}")
    public ResponseEntity<Page<PriceAlert>> allPriceAlertsPaginated(@PathVariable Integer number) {
        var page = PageRequest.of(number, 5);
        var priceAlerts = priceAlertService.allPriceAlertsPaginated(page);
        return ResponseEntity.status(HttpStatus.OK).body(priceAlerts);
    }

    @GetMapping("/{id}")
    public ResponseEntity<PriceAlertResponse> findById(@PathVariable Long id) {
        var priceAlertById = priceAlertService.findPriceAlertById(id);
        var productResponse = PriceAlert.toPriceAlertResponse(priceAlertById);
        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        priceAlertService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
