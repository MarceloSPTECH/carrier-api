package com.jazztech.cardholderapi.mapper;

import com.jazztech.cardholderapi.controller.request.CardHolderRequest;
import com.jazztech.cardholderapi.model.BankAccountModel;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BankAccountMapper {
    BankAccountModel modelFromRequest(CardHolderRequest.BankAccountRequest bankAccountRequest);
}
