package com.glauber.MyLowPrice.service.impl;

import com.glauber.MyLowPrice.controller.exception.ProductAlreadyExistsException;
import com.glauber.MyLowPrice.domain.entities.Product;
import com.glauber.MyLowPrice.domain.repository.ProductPaginatedRepository;
import com.glauber.MyLowPrice.domain.repository.ProductRepository;
import com.glauber.MyLowPrice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Optional;

@Service
public class ProductServiceImpl implements ProductService {
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private ProductPaginatedRepository paginatedRepository;
    @Autowired
    private KafkaTemplate<String, Object> kafkaTemplate;

    @Override
    public Product save(Product product) {
        CheckProduct(product);
        var productSaved = productRepository.save(product);
        kafkaTemplate.send("NEW_PRODUCT", productSaved);
        return productSaved;
    }

    @Override
    public Page<Product> allProductsPaginated(PageRequest pageRequest) {
        return paginatedRepository.findAll(pageRequest);
    }

    @Override
    public Product findProductById(Long id) {
        var productById = productRepository.findById(id);
        if (productById.isEmpty()) {
            throw new NoSuchElementException("Não foi encontrado o produto com o id: " + id + " Informado!");
        }
        return productById.get();
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


    private void CheckProduct(Product product) {
        var savedProduct = productRepository.findByName(product.getName());

        if (savedProduct.isPresent() &&
                Objects.equals(savedProduct.get().getPrice(), product.getPrice()) &&
                savedProduct.get().getLink().equalsIgnoreCase(product.getLink())) {
            throw new ProductAlreadyExistsException("Produto com nome: "
                    + product.getName()
                    + " preço: "
                    + product.getPrice()
                    + " link: " + product.getLink()
                    + " já existe.");
        }
    }
}