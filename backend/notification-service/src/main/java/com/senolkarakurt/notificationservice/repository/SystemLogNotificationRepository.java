package com.senolkarakurt.notificationservice.repository;

import com.senolkarakurt.notificationservice.model.SystemLogNotification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemLogNotificationRepository extends MongoRepository<SystemLogNotification, String> {
}
