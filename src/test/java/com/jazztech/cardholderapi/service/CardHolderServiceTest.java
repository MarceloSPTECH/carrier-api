package com.jazztech.cardholderapi.service;

import static com.jazztech.cardholderapi.service.Factory.cardHolderEntityFactory;
import static com.jazztech.cardholderapi.service.Factory.cardHolderModelFactory;
import static com.jazztech.cardholderapi.service.Factory.cardHolderRequestFactory;
import static com.jazztech.cardholderapi.service.Factory.creditAnalysisDTOFactory;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.when;

import com.jazztech.cardholderapi.apiclient.CreditAnalysisClient;
import com.jazztech.cardholderapi.apiclient.dtos.CreditAnalysisDTO;
import com.jazztech.cardholderapi.controller.response.CardHolderResponse;
import com.jazztech.cardholderapi.handler.exceptions.CardHolderAlreadyRegisteredException;
import com.jazztech.cardholderapi.handler.exceptions.ClientDoesNotCorrespondToCreditAnalysisException;
import com.jazztech.cardholderapi.handler.exceptions.CreditAnalysisNotApprovedException;
import com.jazztech.cardholderapi.handler.exceptions.CreditAnalysisNotFoundException;
import com.jazztech.cardholderapi.handler.exceptions.InvalidCardHolderStatusException;
import com.jazztech.cardholderapi.mapper.BankAccountMapper;
import com.jazztech.cardholderapi.mapper.BankAccountMapperImpl;
import com.jazztech.cardholderapi.mapper.CardHolderMapper;
import com.jazztech.cardholderapi.mapper.CardHolderMapperImpl;
import com.jazztech.cardholderapi.model.CardHolderModel;
import com.jazztech.cardholderapi.repository.CardHolderRepository;
import com.jazztech.cardholderapi.repository.entity.CardHolderEntity;
import com.jazztech.cardholderapi.utils.Status;
import java.util.List;
import java.util.UUID;
import org.junit.jupiter.api.DisplayNameGeneration;
import org.junit.jupiter.api.DisplayNameGenerator;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.dao.DataIntegrityViolationException;

@ExtendWith(MockitoExtension.class)
@DisplayNameGeneration(DisplayNameGenerator.ReplaceUnderscores.class)
class CardHolderServiceTest {

    @Mock
    private CreditAnalysisClient creditAnalysisClient;
    @Mock
    private CardHolderRepository cardHolderRepository;

    @Spy
    private CardHolderMapper cardHolderMapper = new CardHolderMapperImpl();
    @Spy
    private BankAccountMapper bankAccountMapper = new BankAccountMapperImpl();

    @Captor
    private ArgumentCaptor<CardHolderEntity> cardHolderEntityArgumentCaptor;
    @Captor
    private ArgumentCaptor<UUID> creditAnalysisUUID;
    @Captor
    private ArgumentCaptor<Status> cardHolderStatus;

    @InjectMocks
    private CardHolderService cardHolderService;

    @Test
    void should_create_card_holder() {
        when(creditAnalysisClient.getCreditAnalysisById(creditAnalysisUUID.capture())).thenReturn(creditAnalysisDTOFactory());
        when(cardHolderRepository.save(cardHolderEntityArgumentCaptor.capture())).thenReturn(cardHolderEntityFactory());

        final CardHolderResponse cardHolderResponse = cardHolderService.createCardHolder(cardHolderRequestFactory());
        final CardHolderEntity cardHolderEntity = cardHolderEntityArgumentCaptor.getValue();

        assertNotNull(cardHolderResponse.id());
        assertEquals(creditAnalysisUUID.getValue(), creditAnalysisDTOFactory().id());
        assertEquals(creditAnalysisDTOFactory().approvedLimit(), cardHolderResponse.creditLimit());
        assertEquals(creditAnalysisDTOFactory().approvedLimit(), cardHolderResponse.creditLimit());

        assertEquals(cardHolderRequestFactory().clientId(), cardHolderEntity.getClientId());
        assertEquals(cardHolderRequestFactory().creditAnalysisId(), cardHolderEntity.getCreditAnalysisId());

        assertEquals(creditAnalysisDTOFactory().approvedLimit(), cardHolderEntity.getCreditLimit());
    }

    @Test
    void should_throw_CardHolderAlreadyRegisteredException() {
        when(creditAnalysisClient.getCreditAnalysisById(creditAnalysisUUID.capture())).thenReturn(creditAnalysisDTOFactory());
        when(cardHolderRepository.save(cardHolderEntityArgumentCaptor.capture())).thenThrow(DataIntegrityViolationException.class);
        assertThrows(CardHolderAlreadyRegisteredException.class, () -> cardHolderService.createCardHolder(cardHolderRequestFactory()),
                "Card Holder already registered, check the data sent for registration");

    }

    @Test
    void should_throw_CreditAnalysisNotFoundException() {
        final CreditAnalysisDTO creditAnalysisDTO = creditAnalysisDTOFactory().toBuilder().id(null).build();
        when(creditAnalysisClient.getCreditAnalysisById(creditAnalysisUUID.capture())).thenReturn(creditAnalysisDTO);
        final CreditAnalysisNotFoundException exception =
                assertThrows(CreditAnalysisNotFoundException.class, () -> cardHolderService.getCreditAnalysisById(cardHolderModelFactory()));
        assertEquals("Credit analysis not found by ID %s".formatted(cardHolderModelFactory().creditAnalysisId()), exception.getMessage());

    }

    @Test
    void should_throw_ClientDoesNotCorrespondToCreditAnalysisException() {
        final CardHolderModel cardHolderModelModified = cardHolderModelFactory().toBuilder().clientId(UUID.randomUUID()).build();
        when(creditAnalysisClient.getCreditAnalysisById(creditAnalysisUUID.capture())).thenReturn(creditAnalysisDTOFactory());
        final ClientDoesNotCorrespondToCreditAnalysisException exception =
                assertThrows(ClientDoesNotCorrespondToCreditAnalysisException.class,
                        () -> cardHolderService.getCreditAnalysisById(cardHolderModelModified));
        assertEquals("clientId %s does not correspond to credit analysisId %s".formatted(cardHolderModelModified.clientId(),
                creditAnalysisDTOFactory().id()), exception.getMessage());

    }

    @Test
    void should_throw_CreditAnalysisNotApprovedException() {
        final CreditAnalysisDTO creditAnalysisDTOModified = creditAnalysisDTOFactory().toBuilder().approved(false).build();
        when(creditAnalysisClient.getCreditAnalysisById(creditAnalysisUUID.capture())).thenReturn(creditAnalysisDTOModified);
        final CreditAnalysisNotApprovedException exception =
                assertThrows(CreditAnalysisNotApprovedException.class,
                        () -> cardHolderService.getCreditAnalysisById(cardHolderModelFactory()));
        assertEquals("The credit analysis %s wasn't approved".formatted(cardHolderModelFactory().creditAnalysisId()), exception.getMessage());
    }

    @Test
    void should_return_all_card_holders() {
        when(cardHolderRepository.findAll()).thenReturn(List.of(cardHolderEntityFactory()));
        final List<CardHolderResponse> cardHolderResponses = cardHolderService.getAllCardholders();
        assertNotNull(cardHolderResponses);
    }

    @Test
    void should_return_all_card_holders_by_status() {
        when(cardHolderRepository.findAllByStatus(cardHolderStatus.capture())).thenReturn(List.of(cardHolderEntityFactory()));
        final List<CardHolderResponse> cardHolderResponses = cardHolderService.getAllCardholdersByStatus("active");
        assertNotNull(cardHolderResponses);
        assertEquals(cardHolderStatus.getValue(), cardHolderResponses.get(0).status());
    }

    @Test
    void should_throw_InvalidCardHolderStatusException() {
//        when(cardHolderRepository.findAllByStatus(cardHolderStatus.capture())).thenThrow(IllegalArgumentException.class);
        final InvalidCardHolderStatusException exception =
                assertThrows(InvalidCardHolderStatusException.class, () -> cardHolderService.getAllCardholdersByStatus("dsadsa"));
        assertEquals("The informed card holder status is invalid.", exception.getMessage());

    }
}