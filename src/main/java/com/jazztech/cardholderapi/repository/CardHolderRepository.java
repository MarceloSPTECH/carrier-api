package com.jazztech.cardholderapi.repository;

import com.jazztech.cardholderapi.repository.entity.CardHolderEntity;
import com.jazztech.cardholderapi.utils.Status;
import java.util.List;
import java.util.UUID;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardHolderRepository extends JpaRepository<CardHolderEntity, UUID> {
    List<CardHolderEntity> findAllByStatus(Status status);
}
