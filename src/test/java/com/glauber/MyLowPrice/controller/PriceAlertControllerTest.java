package com.glauber.MyLowPrice.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.glauber.MyLowPrice.BaseCompTest;
import com.glauber.MyLowPrice.service.PriceAlertService;
import com.glauber.MyLowPrice.templates.priceAlertTemplate.PriceAlertRequestTemplate;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.is;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class PriceAlertControllerTest extends BaseCompTest {
    private static final String BASE_URL = "/api/alerts";

    private static final String INSERT_INTO_ALERTS = "classpath:/db/sql/insert_into_alert.sql";
    private static final String TRUNCATE_ALERTS = "classpath:/db/sql/reset_alerts.sql";

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PriceAlertService priceAlertService;

    @Test
    @DisplayName("Deve registrar um alerta com sucesso")
    public void itMustSaveANewAlert() throws Exception {
        var priceAlert = PriceAlertRequestTemplate.creation();
        String body = objectMapper.writeValueAsString(priceAlert);
    // O teste só funciona se subir o broker.
        mockMvc
                .perform(post(BASE_URL)
                .contentType(MediaType.APPLICATION_JSON)
                .content(body)).andExpect(status().isCreated());
        var priceAlertById = priceAlertService.findPriceAlertById(1L);
        Assertions.assertEquals(1, priceAlertById.getId());
    }
    @Test
    @DisplayName("Deve listar todos os alertas paginados com sucesso")
    public void itMustListAllPriceAlertsPaginated() throws Exception {
        mockMvc.perform(get(BASE_URL + "/page/1")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    @DisplayName("Deve localizar um alerta especifico com sucesso")
    @Sql({TRUNCATE_ALERTS, INSERT_INTO_ALERTS})
    public void itMustFindAPriceAlertById() throws Exception {
        mockMvc.perform(get("/api/alerts/" + 1)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is2xxSuccessful());
    }
    @Test
    @DisplayName("Deve deletar um alerta")
    @Sql({TRUNCATE_ALERTS, INSERT_INTO_ALERTS})
    public void itMustDeleteAPriceAlert() throws Exception {
        mockMvc.perform(delete("/api/alerts/" + 1)
                .contentType(MediaType.APPLICATION_JSON)).andExpect(status().isNoContent());
    }
    @Test
    @DisplayName("Não deve registrar um alerta se os campos forem inválidos")
    public void itMustNotRegisteAAlertIfFieldsAreInvalid() throws Exception {
        var priceAlert = PriceAlertRequestTemplate.creation();
        priceAlert.setName("");
        priceAlert.setEmail("");
        priceAlert.setPriceRange(0);
        priceAlert.setProductName("");
        String body = objectMapper.writeValueAsString(priceAlert);

        mockMvc
                .perform(post(BASE_URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(body)).andExpect(status().is4xxClientError());
    }
    @Test
    @DisplayName("Deve falhar ao tentar encontrar um alerta com id inválido")
    @Sql({TRUNCATE_ALERTS, INSERT_INTO_ALERTS})
    public void itMustNotFindAPriceALertWithInvalidId() throws Exception {
        mockMvc.perform(get("/api/alerts/" + 0)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError())
                .andExpect(jsonPath("error",
                        is("Não foi localizado um Price Alert com o id:0 informado!")))
                .andExpect(status().isNotFound());
    }
    @Test
    @DisplayName("Não deve deletar um produto com ID inválido")
    public void itMustNotDeleteAPriceAlertIfTheIdIsInvalid() throws Exception {
        mockMvc.perform(delete("/api/alerts/" + 0)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().is4xxClientError());
    }

}