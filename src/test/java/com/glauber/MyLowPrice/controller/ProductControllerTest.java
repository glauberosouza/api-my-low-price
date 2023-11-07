package com.glauber.MyLowPrice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glauber.MyLowPrice.BaseCompTest;
import com.glauber.MyLowPrice.templates.ProductRequestTemplate;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ProductControllerTest extends BaseCompTest {

    private static final String BASE_URL = "/api/products";
    private static final String INSERT_INTO_PRODUCTS = "classpath:db/sql/insert_into_product.sql";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;

    @Test
    @DisplayName("Deve registrar um novo produto com sucesso")
    public void itMustRegisterAProduct() throws Exception {
        var creation = ProductRequestTemplate.creation();
        var body = objectMapper.writeValueAsString(creation);
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isCreated());
    }
    @Test
    @DisplayName("Deve listar todos os produtos cadastrados paginados")
    public void itMustListAllProductsPaginated() throws Exception {
        mockMvc.perform(get(BASE_URL + "/page/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
}
/*
* # TESTES DE INTEGRAÇÃO COM BANCO DE DADOS (CRAWLER)
@DisplayName("Deve registrar um novo produto com sucesso")
@DisplayName("Deve encontrar um produto no banco de dados com sucesso")
@DisplayName("Deve atualizar um produto")
@DisplayName("Deve deletar o produto com sucesso")
* */