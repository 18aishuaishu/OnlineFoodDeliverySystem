package com.Notification_Service.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Notification_Service.Entity.Notification;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, Long> {
    // You can add custom queries if needed
    List<Notification> findByRecipient(String recipient); // Find notifications by recipient (email/phone)
    List<Notification> findByRecipientAndRestaurantName(String recipient, String restaurantName);

}

