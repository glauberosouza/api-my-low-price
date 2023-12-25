package com.glauber.MyLowPrice.service.impl;

import com.glauber.MyLowPrice.domain.entities.PriceAlert;
import com.glauber.MyLowPrice.domain.repository.PriceALertPaginatedRepository;
import com.glauber.MyLowPrice.domain.repository.PriceAlertRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class PriceAlertServiceImplTest {

    @Mock
    private PriceAlertRepository priceAlertRepository;
    @Mock
    private PriceALertPaginatedRepository paginatedRepository;
    @InjectMocks
    private PriceAlertServiceImpl priceAlertService;
    //TODO PARA QUE ESSE TESTE FUNCIONE EU PRECISO USAR OU SIMULAR O KAFKA NOS MEUS TESTES.
    /*@Test
    @DisplayName("Deve salvar um PriceAlert com sucesso")
    public void itMustSaveAPriceAlert(){
        // GIVEN
        var priceAlert = new PriceAlert(
                1L,
                "Glauber",
                "Xbox", 1000.0,
                "glauber@gmail.com");

        when(priceAlertRepository.save(any(PriceAlert.class))).thenReturn(priceAlert);
        // WHEN
        var save = priceAlertService.save(priceAlert);
        // THEN
        Assertions.assertEquals("Glauber", save.getName());
        Assertions.assertEquals("Xbox", save.getProductName());
        Assertions.assertEquals("glauber@gmail.com", save.getEmail());

    }*/
    @Test
    @DisplayName("Deve encontrar um PriceAlert por ID com sucesso")
    public void itMustFindAPriceAlertById(){
        // GIVEN
        var priceAlert = new PriceAlert(
                1L,
                "Glauber",
                "Xbox", 1000.0,
                "glauber@gmail.com");

        when(priceAlertRepository.findById(1L)).thenReturn(Optional.of(priceAlert));
        // WHEN
        var priceAlertById = priceAlertService.findPriceAlertById(1L);
        // THEN
        Assertions.assertEquals("Glauber", priceAlertById.getName());
        Assertions.assertEquals("Xbox", priceAlertById.getProductName());
        Assertions.assertEquals("glauber@gmail.com", priceAlertById.getEmail());
    }
    @Test
    @DisplayName("Deve retornar uma lista paginada de PriceAlerts")
    public void itMustReturnPaginatedListOfPriceAlerts(){
        // GIVEN
        var pageRequest = PageRequest.of(0, 10);
        var page =
                new PageImpl<>(
                        List.of(new PriceAlert(
                                1L,
                                "Glauber",
                                "Xbox",
                                1000.0,
                                "glauber@gmail.com")));
        when(paginatedRepository.findAll(pageRequest)).thenReturn(page);

        // WHEN
        var resultPage = priceAlertService.allPriceAlertsPaginated(pageRequest);

        // THEN
        assertTrue(resultPage.getContent().stream().anyMatch(content -> content.getName().equals("Glauber")));
        verify(paginatedRepository, times(1)).findAll(pageRequest);
    }

    @Test
    @DisplayName("Deve excluir um PriceAlert com sucesso")
    public void itMustDeleteAPriceAlert(){
        // GIVEN
        var priceAlert = new PriceAlert(
                1L,
                "Glauber",
                "Xbox", 1000.0,
                "glauber@gmail.com");

        when(priceAlertRepository.findById(1L)).thenReturn(Optional.of(priceAlert));
        // WHEN
        priceAlertService.delete(1L);
        var all = priceAlertRepository.findAll();
        // THEN
        Assertions.assertEquals(0, all.size());
    }
}