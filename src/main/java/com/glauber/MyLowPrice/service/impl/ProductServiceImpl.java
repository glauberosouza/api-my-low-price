package com.glauber.MyLowPrice.service.impl;

import com.glauber.MyLowPrice.configuration.kafkaConfig.KafkaProducerConfig;
import com.glauber.MyLowPrice.domain.entities.Product;
import com.glauber.MyLowPrice.domain.repository.ProductPaginatedRepository;
import com.glauber.MyLowPrice.domain.repository.ProductRepository;
import com.glauber.MyLowPrice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductPaginatedRepository paginatedRepository;
    @Autowired
    private KafkaProducerConfig kafkaProducerConfig;

    @Override
    public Product save(Product product) {
        var productSaved = productRepository.save(product);
        kafkaProducerConfig.kafkaTemplate()
                .send("NEW_PRODUCT", "Novo produto cadastrado: " + productSaved.getName());
        return productSaved;
    }

    @Override
    public Page<Product> allProductsPaginated(PageRequest pageRequest) {
        return paginatedRepository.findAll(pageRequest);
    }

    @Override
    public Product update(Long id, Product product) {
        var productById = productRepository.findById(id);
        if (productById.isEmpty()) {
            throw new NoSuchElementException("Não foi encontrado o produto com o id: " + id + " Informado!");
        }
        var productToUpdate = productById.get();
        productToUpdate.setName(product.getName());
        productToUpdate.setLink(product.getLink());
        productToUpdate.setPrice(product.getPrice());
        productRepository.save(productToUpdate);
        return productToUpdate;
    }

    @Override
    public void delete(Long id) {
        var productById = productRepository.findById(id);
        if (productById.isEmpty()) {
            throw new NoSuchElementException("Não foi encontrado o produto com o id: " + id + " Informado!");
        }
        var productToDelete = productById.get();
        productRepository.delete(productToDelete);
    }

}
