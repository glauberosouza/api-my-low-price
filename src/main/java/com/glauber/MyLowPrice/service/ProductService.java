package com.glauber.MyLowPrice.service;

import com.glauber.MyLowPrice.domain.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface ProductService {
    Product save(Product product);
    Page<Product> allProductsPaginated(PageRequest pageRequest);
    Product findProductById(Long id);
    Product update(Long id, Product product);
    void delete(Long id);
}
