package com.glauber.MyLowPrice.domain.repository;

import com.glauber.MyLowPrice.domain.entities.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
}
