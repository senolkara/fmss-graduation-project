package com.senolkarakurt.purchaseservice.repository;

import com.senolkarakurt.enums.RecordStatus;
import com.senolkarakurt.purchaseservice.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Optional<Purchase> findByIdAndAndRecordStatus(Long id, RecordStatus recordStatus);
}
