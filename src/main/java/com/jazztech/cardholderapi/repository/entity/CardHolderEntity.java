package com.jazztech.cardholderapi.repository.entity;

import com.jazztech.cardholderapi.utils.Status;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "CARD_HOLDER")
@Immutable
public class CardHolderEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    UUID clientId;

    UUID creditAnalysisId;

    @Enumerated(EnumType.STRING)
    Status status;

    BigDecimal creditLimit;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "bank_account_id", referencedColumnName = "id")
    BankAccountEntity bankAccountEntity;

    @Builder(toBuilder = true)
    public CardHolderEntity(UUID id, UUID clientId, UUID creditAnalysisId, Status status, BigDecimal creditLimit, LocalDateTime createdAt,
                            LocalDateTime updatedAt, BankAccountEntity bankAccountEntity) {
        this.id = id;
        this.clientId = clientId;
        this.creditAnalysisId = creditAnalysisId;
        this.status = status;
        this.creditLimit = creditLimit;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.bankAccountEntity = bankAccountEntity;
    }

    private CardHolderEntity() {
    }

    public UUID getId() {
        return id;
    }

    public UUID getClientId() {
        return clientId;
    }

    public UUID getCreditAnalysisId() {
        return creditAnalysisId;
    }

    public Status getStatus() {
        return status;
    }

    public BigDecimal getCreditLimit() {
        return creditLimit;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public BankAccountEntity getBankAccountEntity() {
        return bankAccountEntity;
    }

    @Override
    public String toString() {
        return "CardHolderEntity{"
                +
                "id=" + id
                +
                ", clientId=" + clientId
                +
                ", creditAnalysisId=" + creditAnalysisId
                +
                ", status=" + status
                +
                ", creditLimit=" + creditLimit
                +
                ", createdAt=" + createdAt
                +
                ", updatedAt=" + updatedAt
                +
                ", bankAccountEntity=" + bankAccountEntity
                +
                '}';
    }
}
