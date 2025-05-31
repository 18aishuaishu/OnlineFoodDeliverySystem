package com.Customer_Service.FeignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.Customer_Service.DTO.NotificationDTO;
import com.Customer_Service.Entity.Notification;




@FeignClient(name = "notification", url = "http://localhost:2006/notifications") 

public interface NotificationClient {

    // Endpoint for sending notifications
    @PostMapping("/send")
    Notification sendNotification(@RequestBody NotificationDTO notificationDTO);
    //no need for customer
    @PutMapping("/{id}/status")
    void updateNotificationStatus(@PathVariable Long id, @RequestParam String status);
    //no need for customer

    @GetMapping("/customer/{recipient}/restaurant/{restaurantName}")
    List<Notification> getNotificationsByRecipientAndRestaurant(
            @PathVariable("recipient") String recipient,
            @PathVariable("restaurantName") String restaurantName
    );

}