package com.senolkarakurt.purchaseservice.repository;

import com.senolkarakurt.purchaseservice.model.Invoice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceRepository extends JpaRepository<Invoice, Long> {
}
