package com.jazztech.cardholderapi.service;


import com.jazztech.cardholderapi.apiclient.dtos.CreditAnalysisDTO;
import com.jazztech.cardholderapi.controller.request.CardHolderRequest;
import com.jazztech.cardholderapi.model.BankAccountModel;
import com.jazztech.cardholderapi.model.CardHolderModel;
import com.jazztech.cardholderapi.repository.entity.BankAccountEntity;
import com.jazztech.cardholderapi.repository.entity.CardHolderEntity;
import com.jazztech.cardholderapi.utils.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

class Factory {

    static final UUID MOCKED_UUID = UUID.fromString("1a17e5da-028e-4bc5-a61c-a1d8bdab1416");
    static final BigDecimal MOCKED_CREDIT_LIMIT = BigDecimal.valueOf(1500);


    static final String MOCKED_ACCOUNT = "27184771-6";
    static final Short MOCKED_AGENCY = 1121;
    static final Short MOCKED_BANK_CODE = 302;


    static CreditAnalysisDTO creditAnalysisDTOFactory() {
        return CreditAnalysisDTO.builder()
                .id(MOCKED_UUID)
                .clientId(MOCKED_UUID)
                .approved(true)
                .approvedLimit(MOCKED_CREDIT_LIMIT)
                .build();
    }

    static CardHolderRequest.BankAccountRequest bankAccountRequestFactory() {
        return CardHolderRequest.BankAccountRequest.builder()
                .account(MOCKED_ACCOUNT)
                .agency(String.valueOf(MOCKED_AGENCY))
                .bankCode(String.valueOf(MOCKED_BANK_CODE))
                .build();
    }


    static BankAccountModel bankAccountModelFactory() {
        return BankAccountModel.builder()
                .account(MOCKED_ACCOUNT)
                .agency(MOCKED_AGENCY)
                .bankCode(MOCKED_BANK_CODE)
                .build();
    }


    static BankAccountEntity bankAccountEntityFactory() {
        return BankAccountEntity.builder()
                .id(MOCKED_UUID)
                .account(MOCKED_ACCOUNT)
                .agency(MOCKED_AGENCY)
                .bankCode(MOCKED_BANK_CODE)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .build();
    }

    static CardHolderRequest cardHolderRequestFactory() {
        return CardHolderRequest.builder()
                .clientId(MOCKED_UUID)
                .creditAnalysisId(MOCKED_UUID)
                .bankAccount(bankAccountRequestFactory())
                .build();
    }


    static CardHolderModel cardHolderModelFactory() {
        return CardHolderModel.builder()
                .clientId(MOCKED_UUID)
                .creditAnalysisId(MOCKED_UUID)
                .status(Status.ACTIVE)
                .creditLimit(MOCKED_CREDIT_LIMIT)
                .bankAccount(bankAccountModelFactory())
                .build();
    }


    static CardHolderEntity cardHolderEntityFactory() {
        return CardHolderEntity.builder()
                .id(MOCKED_UUID)
                .clientId(MOCKED_UUID)
                .creditAnalysisId(MOCKED_UUID)
                .status(Status.ACTIVE)
                .creditLimit(MOCKED_CREDIT_LIMIT)
                .createdAt(LocalDateTime.now())
                .updatedAt(LocalDateTime.now())
                .bankAccount(bankAccountEntityFactory())
                .build();
    }

}
