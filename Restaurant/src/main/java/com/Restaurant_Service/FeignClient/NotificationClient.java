package com.Restaurant_Service.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;

import com.Restaurant_Service.DTO.NotificationDTO;
import com.Restaurant_Service.Entity.Notification;


@FeignClient(name = "notification", url = "http://localhost:2006/notifications") 

public interface NotificationClient {

    // Endpoint for sending notifications
    @PostMapping("/send")
    Notification sendNotification(@RequestBody NotificationDTO notificationDTO);
    @PutMapping("/{id}/status")
    void updateNotificationStatus(@PathVariable Long id, @RequestParam String status);

}