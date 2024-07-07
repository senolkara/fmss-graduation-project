package com.senolkarakurt.cpackageservice.repository;

import com.senolkarakurt.enums.RecordStatus;
import com.senolkarakurt.cpackageservice.model.CustomerPackage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface CustomerPackageRepository extends JpaRepository<CustomerPackage, Long>, JpaSpecificationExecutor<CustomerPackage> {
    List<CustomerPackage> findAllByCustomerId(Long customerId);
    Optional<CustomerPackage> findByIdAndRecordStatus(Long id, RecordStatus recordStatus);
    Optional<CustomerPackage> findByIdAndFinishDateTimeGreaterThanAndRecordStatus(Long id, LocalDateTime finishDateTime, RecordStatus recordStatus);
    List<CustomerPackage> findAllByCustomerIdAndFinishDateTimeGreaterThanAndRecordStatusOrderByFinishDateTimeDesc(Long customerId, LocalDateTime finishDateTime, RecordStatus recordStatus);
}
