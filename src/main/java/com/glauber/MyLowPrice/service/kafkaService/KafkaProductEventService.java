package com.glauber.MyLowPrice.service.kafkaService;

import com.glauber.MyLowPrice.domain.entities.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class KafkaProductEventService {
    private final KafkaTemplate<String, Product> kafkaTemplate;
    private final String productTopic = "NEW_PRODUCT_TOPIC";
    @Autowired
    public KafkaProductEventService(KafkaTemplate<String, Product> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNewEventForNewProduct(Product product) {
        kafkaTemplate.send(productTopic, product);
    }
}
