package com.glauber.MyLowPrice.domain.repository;

import com.glauber.MyLowPrice.domain.entities.PriceAlert;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
@Repository
public interface PriceAlertRepository extends JpaRepository<PriceAlert, Long> {

}
