package com.jazztech.cardholderapi.model;

import com.jazztech.cardholderapi.utils.Status;
import java.math.BigDecimal;
import java.util.UUID;
import lombok.Builder;

@Builder(toBuilder = true)
public record CardHolderModel(UUID clientId, UUID creditAnalysisId, Status status, BigDecimal creditLimit, BankAccountModel bankAccountModel) {
    public CardHolderModel updateModel(BankAccountModel bankAccountModel, BigDecimal creditLimit) {
        return this.toBuilder()
                .status(Status.ACTIVE)
                .bankAccountModel(bankAccountModel)
                .creditLimit(creditLimit)
                .build();
    }
}
