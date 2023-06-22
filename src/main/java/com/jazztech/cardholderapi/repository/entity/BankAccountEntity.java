package com.jazztech.cardholderapi.repository.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.Builder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Immutable;
import org.hibernate.annotations.UpdateTimestamp;

@Entity
@Table(name = "BANK_ACCOUNT")
@Immutable
public class BankAccountEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    UUID id;

    String account;

    Short agency;

    Short bankCode;

    @CreationTimestamp
    LocalDateTime createdAt;

    @UpdateTimestamp
    LocalDateTime updatedAt;


    @Builder(toBuilder = true)
    public BankAccountEntity(UUID id, String account, Short agency, Short bankCode, LocalDateTime createdAt, LocalDateTime updatedAt) {
        this.id = id;
        this.account = account;
        this.agency = agency;
        this.bankCode = bankCode;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    private BankAccountEntity() {
    }

    public UUID getId() {
        return id;
    }

    public String getAccount() {
        return account;
    }

    public Short getAgency() {
        return agency;
    }

    public Short getBankCode() {
        return bankCode;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public String toString() {
        return "BankAccountEntity{"
                +
                "id=" + id
                +
                ", account='" + account + '\''
                +
                ", agency=" + agency
                +
                ", bankCode=" + bankCode
                +
                ", createdAt=" + createdAt
                +
                ", updatedAt=" + updatedAt
                +
                '}';
    }
}
