package com.glauber.MyLowPrice.domain.repository;

import com.glauber.MyLowPrice.domain.entities.PriceAlert;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceALertPaginatedRepository extends PagingAndSortingRepository<PriceAlert, Long> {
}
