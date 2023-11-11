package com.glauber.MyLowPrice.domain.repository;

import com.glauber.MyLowPrice.domain.entities.PriceAlert;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PriceAlertRepository extends JpaRepository<PriceAlert, Long> {
}
