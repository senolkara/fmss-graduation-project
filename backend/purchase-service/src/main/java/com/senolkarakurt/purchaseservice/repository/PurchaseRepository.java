package com.senolkarakurt.purchaseservice.repository;

import com.senolkarakurt.enums.RecordStatus;
import com.senolkarakurt.purchaseservice.model.Purchase;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface PurchaseRepository extends JpaRepository<Purchase, Long> {
    Optional<Purchase> findByIdAndRecordStatus(Long id, RecordStatus recordStatus);
    List<Purchase> findByOrderIdAndRecordStatus(Long orderId, RecordStatus recordStatus);
}
