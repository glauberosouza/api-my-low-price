package com.glauber.MyLowPrice.domain.repository;

import com.glauber.MyLowPrice.domain.entities.Product;
import org.springframework.data.repository.PagingAndSortingRepository;

public interface ProductPaginatedRepository extends PagingAndSortingRepository<Product, Long> {
}