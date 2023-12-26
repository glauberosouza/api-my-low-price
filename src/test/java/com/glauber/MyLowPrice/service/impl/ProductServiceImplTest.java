package com.glauber.MyLowPrice.service.impl;

import com.glauber.MyLowPrice.domain.entities.Product;
import com.glauber.MyLowPrice.domain.repository.ProductPaginatedRepository;
import com.glauber.MyLowPrice.domain.repository.ProductRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ProductServiceImplTest {
    @Mock
    private ProductRepository productRepository;
    @Mock
    private ProductPaginatedRepository paginatedRepository;
    @InjectMocks
    private ProductServiceImpl productService;
    //TODO PARA QUE ESSE TESTE FUNCIONE EU PRECISO USAR OU SIMULAR O KAFKA NOS MEUS TESTES.
    /*@Test
    @DisplayName("Deve salvar um produto com sucesso")
    public void itMustSaveProductSuccessfully() {
        // Arrange
        var product = new Product(
                1L,
                "Test Product",
                "http://test-product.com", BigDecimal.valueOf(100));

        when(productRepository.save(any(Product.class))).thenReturn(product);

        // Act
        Product savedProduct = productService.save(product);

        //Assert
        assertEquals("Test Product", savedProduct.getName());
        assertEquals("http://test-product.com", savedProduct.getLink());
        assertEquals(BigDecimal.valueOf(100), savedProduct.getPrice());
        verify(productRepository, times(1)).save(any(Product.class));
    }*/

    @Test
    @DisplayName("Deve retornar uma lista paginada de produtos")
    public void itMustReturnPaginatedListOfProducts() {
        // Arrange
        var pageRequest = PageRequest.of(0, 10);
        var page =
                new PageImpl<>(
                        List.of(new Product(
                                1L,
                                "Test Product",
                                "http://test-product.com",
                                BigDecimal.valueOf(100))));
        when(paginatedRepository.findAll(pageRequest)).thenReturn(page);

        // Act
        var resultPage = productService.allProductsPaginated(pageRequest);

        //Assert
        assertTrue(resultPage.getContent().stream().anyMatch(content -> content.getName().equals("Test Product")));
        verify(paginatedRepository, times(1)).findAll(pageRequest);
    }

    @Test
    @DisplayName("Deve retornar um produto pelo id")
    public void itMustFindProductById() {
        // Arrange
        var product = new Product(
                1L,
                "Test Product",
                "http://test-product.com", BigDecimal.valueOf(100));

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        var productById = productService.findProductById(1L);

        //Assert
        Assertions.assertEquals("Test Product", productById.getName());
    }

    @Test
    @DisplayName("Deve deletar um produto pelo id")
    public void itMustDeletAProduct() {
        // Arrange
        var product = new Product(
                1L,
                "Test Product",
                "http://test-product.com", BigDecimal.valueOf(100));

        when(productRepository.findById(1L)).thenReturn(Optional.of(product));

        // Act
        productService.delete(1L);
        var all = productRepository.findAll();

        //Assert
        Assertions.assertEquals(0, all.size());
    }
}