package com.Admin_Service.FeignClient;

import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

import com.Admin_Service.DTO.NotificationDTO;
import com.Admin_Service.Entity.Notification;

@FeignClient(name = "Notification", url = "http://localhost:2006/notifications") // URL or Service Name for
																						// Notification Service
public interface NotificationServiceFeignClient {

	// Send SMS Notification
	@PostMapping("/sms")
	Notification sendSms(@RequestBody NotificationDTO notificationDTO);

	// Send generic notification
	@PostMapping("/send")
	Notification sendNotification(@RequestBody NotificationDTO notificationDTO);

	// Send Email Notification
	@PostMapping("/email")
	Notification sendEmail(@RequestBody NotificationDTO notificationDTO);

	// Send WhatsApp Notification
	@PostMapping("/whatsapp")
	Notification sendWhatsAppNotification(@RequestBody NotificationDTO notificationDTO);

	// Send Push Notification
	@PostMapping("/push")
	Notification sendPushNotification(@RequestBody NotificationDTO notificationDTO);

	// Get all notifications
	@GetMapping("/all")
	List<Notification> getAllNotifications();

	// Get notification by ID
	@GetMapping("/{id}")
	Notification getNotificationById(@PathVariable Long id);

}
