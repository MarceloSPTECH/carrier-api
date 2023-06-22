package com.jazztech.cardholderapi.controller.response;

import com.jazztech.cardholderapi.utils.Status;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;

@Builder(toBuilder = true)
public record CardHolderResponse(UUID id, Status status, BigDecimal creditLimit, LocalDateTime createdAt, LocalDateTime updatedAt) {
}
