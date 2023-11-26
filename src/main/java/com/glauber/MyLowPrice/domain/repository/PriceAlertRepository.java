package com.glauber.MyLowPrice.domain.repository;

import com.glauber.MyLowPrice.domain.entities.PriceAlert;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PriceAlertRepository extends JpaRepository<PriceAlert, Long> {
    Optional<PriceAlert> findByProductName(String productName);
    Optional<PriceAlert> findByUserName(String Username);
    Optional<PriceAlert> findByPriceRange(Double priceRange);
}
