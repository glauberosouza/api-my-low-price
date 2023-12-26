package com.glauber.MyLowPrice.service;

import com.glauber.MyLowPrice.domain.entities.PriceAlert;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;

public interface PriceAlertService {
    PriceAlert save(PriceAlert priceAlert);
    PriceAlert findPriceAlertById(Long id);

    Page<PriceAlert> allPriceAlertsPaginated(PageRequest pageRequest);

    void delete(Long id);
}