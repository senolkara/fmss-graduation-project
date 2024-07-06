package com.senolkarakurt.notificationservice.repository;

import com.senolkarakurt.notificationservice.model.AdvertisementNotification;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AdvertisementNotificationRepository extends MongoRepository<AdvertisementNotification, String> {
}
