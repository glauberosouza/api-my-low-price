package com.glauber.MyLowPrice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glauber.MyLowPrice.BaseCompTest;
import com.glauber.MyLowPrice.domain.entities.Product;
import com.glauber.MyLowPrice.service.ProductService;
import com.glauber.MyLowPrice.templates.productTemplate.ProductRequestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.math.BigDecimal;

import static org.hamcrest.Matchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


class ProductControllerTest extends BaseCompTest {

    private static final String BASE_URL = "/api/products";
    private static final String INSERT_INTO_PRODUCTS = "classpath:db/sql/insert_into_product.sql";
    private static final String UPDATE_PRODUCT = "classpath:db/sql/update_product.sql";
    private static final String TRUNCATE = "classpath:db/sql/reset.sql";
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ProductService productService;
    @Mock
    private KafkaTemplate<String, String> kafkaTemplate;

    @Test
    @DisplayName("Deve registrar um novo produto com sucesso")
    public void itMustRegisterAProduct() throws Exception {
        var creation = ProductRequestTemplate.creation();
        var body = objectMapper.writeValueAsString(creation);
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().isCreated());
        Product productById = productService.findProductById(1L);
        Assertions.assertEquals(1, productById.getId());
    }
    @Test
    @DisplayName("Deve listar todos os produtos cadastrados paginados")
    public void itMustListAllProductsPaginated() throws Exception {
        mockMvc.perform(get(BASE_URL + "/page/1")
                .contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk());
    }
    @Test
    @DisplayName("Deve encontrar um produto no banco de dados com sucesso")
    @Sql({TRUNCATE, INSERT_INTO_PRODUCTS})
    public void itMustFindAProductInDatabase() throws Exception {
        mockMvc.perform(get("/api/products/" + 1).contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("Xbox")));
    }
    @Test
    @DisplayName("Deve atualizar um produto")
    @Sql({INSERT_INTO_PRODUCTS, UPDATE_PRODUCT})
    public void itMustUpdateAProduct() throws Exception {
        var creation = ProductRequestTemplate.update();
        var body = objectMapper.writeValueAsString(creation);
        mockMvc.perform(put("/api/products/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andExpect(jsonPath("name", is("Xbox360")))
                .andExpect(jsonPath("link", is("@Xbox360")))
                .andExpect(jsonPath("price", is(4000.00)));
    }
    @Test
    @DisplayName("Deve deletar o produto com sucesso")
    @Sql(INSERT_INTO_PRODUCTS)
    public void itMustDeleteAProduct() throws Exception {
        mockMvc.perform(delete("/api/products/" + 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }
    @Test
    @DisplayName("Não deve registrar um novo produto se os campos forem inválidos")
    public void shouldNotRegisterANewProductIfFieldsAreInvalid() throws Exception {

        var creation = ProductRequestTemplate.creation();
        creation.setName("");
        creation.setLink("");
        creation.setPrice(BigDecimal.valueOf(0));
        var body = objectMapper.writeValueAsString(creation);
        mockMvc.perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)
        ).andExpect(status().is4xxClientError());
    }
    @Test
    @DisplayName("Deve falhar ao tentar encontrar um produto com id inválido")
    @Sql({TRUNCATE, INSERT_INTO_PRODUCTS})
    public void shouldFailWhenFindANonExistentProductInTheDatabase() throws Exception {
        mockMvc.perform(get("/api/products/" + 0).contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("error",
                        is("Não foi encontrado o produto com o id: 0 Informado!")))
                .andExpect(status().isNotFound());
    }
    @Test
    @DisplayName("Não deve atualizar um produto com campos inválidos")
    @Sql({INSERT_INTO_PRODUCTS, UPDATE_PRODUCT})
    public void shouldNotUpdateAProductWithInvalidFields() throws Exception {
        var creation = ProductRequestTemplate.update();
        creation.setName("");
        var body = objectMapper.writeValueAsString(creation);
        mockMvc.perform(put("/api/products/" + 1)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is4xxClientError());
    }
    @Test
    @DisplayName("Não deve deletar um produto com ID inválido")
    @Sql(INSERT_INTO_PRODUCTS)
    public void shouldNotDeleteAProductWithAnInvalidId() throws Exception {
        mockMvc.perform(delete("/api/products/" + 0)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}
