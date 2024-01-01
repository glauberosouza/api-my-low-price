package com.glauber.MyLowPrice.service.impl;

import com.glauber.MyLowPrice.domain.entities.PriceAlert;
import com.glauber.MyLowPrice.domain.repository.PriceALertPaginatedRepository;
import com.glauber.MyLowPrice.domain.repository.PriceAlertRepository;
import com.glauber.MyLowPrice.service.PriceAlertService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
//TODO LIBERAR CADASTRO DE PRODUTOS COM NOME SEMELHANTE.
@Service
public class PriceAlertServiceImpl implements PriceAlertService {
    @Autowired
    private PriceAlertRepository priceAlertRepository;

    @Autowired
    private PriceALertPaginatedRepository paginatedRepository;

    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;


    @Override
    public PriceAlert save(PriceAlert priceAlert) {
        var priceAlertSaved = priceAlertRepository.save(priceAlert);
        kafkaTemplate.send("NEW_PRICE_ALERT", priceAlertSaved);
        return priceAlertSaved;
    }

    @Override
    public PriceAlert findPriceAlertById(Long id) {
        var priceAlertById = priceAlertRepository.findById(id);
        if (priceAlertById.isEmpty()) {
            throw new NoSuchElementException("Não foi localizado um Price Alert com o id:" + id + " informado!");
        }
        return priceAlertById.get();
    }

    @Override
    public Page<PriceAlert> allPriceAlertsPaginated(PageRequest pageRequest) {
        return paginatedRepository.findAll(pageRequest);
    }

    @Override
    public void delete(Long id) {
        var priceAlertById = priceAlertRepository.findById(id);
        if (priceAlertById.isEmpty()) {
            throw new NoSuchElementException("Não foi localizado um Price Alert com o id:" + id + " informado!");
        }
        var priceAlert = priceAlertById.get();
        priceAlertRepository.delete(priceAlert);
    }
}