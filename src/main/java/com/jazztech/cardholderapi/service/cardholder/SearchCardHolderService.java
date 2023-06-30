package com.jazztech.cardholderapi.service.cardholder;

import com.jazztech.cardholderapi.controller.response.CardHolderResponse;
import com.jazztech.cardholderapi.handler.exceptions.InvalidCardHolderStatusException;
import com.jazztech.cardholderapi.mapper.CardHolderMapper;
import com.jazztech.cardholderapi.repository.CardHolderRepository;
import com.jazztech.cardholderapi.repository.entity.cardholder.CardHolderEntity;
import com.jazztech.cardholderapi.service.ServiceVerifications;
import com.jazztech.cardholderapi.utils.Status;
import java.util.List;
import java.util.UUID;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class SearchCardHolderService {
    private final CardHolderMapper cardHolderMapper;
    private final CardHolderRepository cardHolderRepository;
    private final ServiceVerifications serviceVerifications;

    public List<CardHolderResponse> getAllCardholders() {
        final List<CardHolderEntity> cardHolderEntities = cardHolderRepository.findAll();
        return cardHolderEntities.stream().map(cardHolderMapper::responseFromEntity).toList();
    }

    public List<CardHolderResponse> getAllCardholdersByStatus(String status) {
        final String statusUpperCase = status.toUpperCase();
        try {
            final List<CardHolderEntity> cardHolderEntities = cardHolderRepository.findAllByStatus(Status.valueOf(statusUpperCase));
            return cardHolderEntities.stream().map(cardHolderMapper::responseFromEntity).toList();
        } catch (IllegalArgumentException e) {
            throw new InvalidCardHolderStatusException("The informed card holder status is invalid.");
        }
    }

    public CardHolderResponse getCardHolderById(UUID id) {
        final CardHolderEntity cardHolderEntity = serviceVerifications.getCardHolderById(id);
        return cardHolderMapper.responseFromEntity(cardHolderEntity);
    }
}
