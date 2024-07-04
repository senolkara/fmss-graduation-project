package com.senolkarakurt.notificationservice.repository;

import com.senolkarakurt.notificationservice.model.InvoiceNotification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface InvoiceNotificationRepository extends MongoRepository<InvoiceNotification, String> {
}
