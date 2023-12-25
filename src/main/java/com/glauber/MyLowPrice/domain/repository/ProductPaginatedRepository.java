package com.glauber.MyLowPrice.domain.repository;

import com.glauber.MyLowPrice.domain.entities.Product;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductPaginatedRepository extends PagingAndSortingRepository<Product, Long> {
}