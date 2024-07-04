package com.senolkarakurt.notificationservice.repository;

import com.senolkarakurt.notificationservice.model.CustomerNotification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CustomerNotificationRepository extends MongoRepository<CustomerNotification, String> {
}
