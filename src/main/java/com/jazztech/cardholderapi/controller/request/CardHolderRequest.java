package com.jazztech.cardholderapi.controller.request;

import com.jazztech.cardholderapi.utils.CustomValidation;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import java.util.UUID;
import lombok.Builder;

@Builder(toBuilder = true)
public record CardHolderRequest(
        @NotNull(message = "clientId cannot be null")
        UUID clientId,
        @NotNull(message = "creditAnalysisId cannot be null")
        UUID creditAnalysisId,
        BankAccountRequest bankAccountRequest
) {

    public CardHolderRequest(UUID clientId, UUID creditAnalysisId, BankAccountRequest bankAccountRequest) {
        this.clientId = clientId;
        this.creditAnalysisId = creditAnalysisId;
        this.bankAccountRequest = bankAccountRequest;
        CustomValidation.validator(this);
    }

    @Builder(toBuilder = true)
    public record BankAccountRequest(
            @Pattern(regexp = "\\d{8}-\\d", message = "Invalid account")
            String account,
            @Pattern(regexp = "\\d{4}", message = "Invalid agency")
            String agency,
            @Pattern(regexp = "\\d{3}", message = "Invalid bankCode")
            String bankCode) {

        public BankAccountRequest(String account, String agency, String bankCode) {
            this.account = account;
            this.agency = agency;
            this.bankCode = bankCode;
            CustomValidation.validator(this);
        }
    }
}
