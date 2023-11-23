package com.glauber.MyLowPrice.controller;

import com.glauber.MyLowPrice.controller.request.ProductRequest;
import com.glauber.MyLowPrice.controller.response.ProductResponse;
import com.glauber.MyLowPrice.domain.entities.Product;
import com.glauber.MyLowPrice.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

//TODO:Configurar para quando um produto for persistido no banco de dados, seja lançado um evento para fila no kafka
//TODO:Analisar o erro no kafka após o envio da primeira mensagem para fila o mesmo parou de funcionar.

@RestController
@RequestMapping("/api/products")
public class ProductController {
    private final ProductService productService;

    @Autowired
    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @PostMapping
    public ResponseEntity<Void> save(@RequestBody @Valid ProductRequest productRequest) {
        var product = ProductRequest.toProductEntity(productRequest);
        productService.save(product);
        return ResponseEntity.status(HttpStatus.CREATED).build();
    }

    @GetMapping("/page/{number}")
    public ResponseEntity<Page<Product>> allProductsPaginated(@PathVariable Integer number) {
        var page = PageRequest.of(number, 5);
        var products = productService.allProductsPaginated(page);
        return ResponseEntity.status(HttpStatus.OK).body(products);
    }

    @GetMapping("{productId}")
    public ResponseEntity<ProductResponse> findAProduct(@PathVariable Long productId) {
        var productById = productService.findProductById(productId);
        var productResponse = Product.toProductResponse(productById);
        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductResponse> update(@PathVariable Long id, @Valid @RequestBody ProductRequest productRequest) {
        var productEntity = ProductRequest.toProductEntity(productRequest);
        var productUpdated = productService.update(id, productEntity);
        var productResponse = Product.toProductResponse(productUpdated);
        return ResponseEntity.status(HttpStatus.OK).body(productResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        productService.delete(id);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}