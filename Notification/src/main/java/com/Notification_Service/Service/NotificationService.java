package com.Notification_Service.Service;

import com.Notification_Service.Entity.Notification;
import com.Notification_Service.Repository.NotificationRepository;
import com.Notification_Service.DTO.NotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class NotificationService {

    @Autowired
    private NotificationRepository notificationRepository;

    // Send SMS Notification
    public Notification sendSmsNotification(NotificationDTO notificationDTO) {
        // Logic to send SMS (could integrate with an SMS API like Twilio)
        Notification notification = new Notification(notificationDTO.getRecipient(), notificationDTO.getMessage(), "SMS", "Sent", notificationDTO.getRestaurantName());
        return notificationRepository.save(notification);
    }

    // Send Email Notification
    public Notification sendEmailNotification(NotificationDTO notificationDTO) {
        // Logic to send Email (could integrate with an Email API like SendGrid or SMTP)
        Notification notification = new Notification(notificationDTO.getRecipient(), notificationDTO.getMessage(), "Email", "Sent", notificationDTO.getRestaurantName());
        return notificationRepository.save(notification);
        
    }
    //whatsapp
    public Notification sendWhatsAppNotification(NotificationDTO notificationDTO) {
        // Logic to send WhatsApp (could integrate with an API like Twilio or WhatsApp Business API)
        // Here we ensure the restaurant name is provided and not null
        Notification notification = new Notification(
            notificationDTO.getRecipient(),
            notificationDTO.getMessage(),
            "Whatsapp",  // Notification type
            "Sent",  // Status (can be updated based on the actual result of the send)
            notificationDTO.getRestaurantName()  // Using the restaurant name from the DTO
        );

        // Save the notification to the database
        return notificationRepository.save(notification);
    }

    
//    private void sendPushNotification(NotificationDTO notificationDTO) {
//        // Logic for sending push notification (e.g., via Firebase Cloud Messaging, OneSignal, etc.)
//        System.out.println("Sending Push Notification to: " + notificationDTO.getRecipient());
//        System.out.println("Message: " + notificationDTO.getMessage());
//    }

    // Send Push Notification
    public Notification sendPushNotification(NotificationDTO notificationDTO) {
        // Logic to send Push Notification (could integrate with Firebase or any other service)
        Notification notification = new Notification(notificationDTO.getRecipient(), notificationDTO.getMessage(), "Push", "Sent", notificationDTO.getRestaurantName());
        return notificationRepository.save(notification);
    }


    public List<Notification> getNotificationsByRecipientAndRestaurant(String recipient, String restaurantName) {
        return notificationRepository.findByRecipientAndRestaurantName(recipient, restaurantName);
    }

    public Notification sendNotification(NotificationDTO notificationDTO) {
        // Step 1: Validate notification DTO
        if (notificationDTO == null || notificationDTO.getRecipient() == null || notificationDTO.getMessage() == null) {
            throw new IllegalArgumentException("Invalid Notification Data");
        }

        // Step 2: Create Notification entity to save in the database
        Notification notification = new Notification(
                notificationDTO.getRecipient(),
                notificationDTO.getMessage(),
                notificationDTO.getType(),
                "Pending", // Status can be dynamically changed based on the result
                notificationDTO.getRestaurantName()
        );

        // Save the notification to the database
        notification = notificationRepository.save(notification);

        // Step 3: Send the actual notification based on the type (SMS, Email, Push, WhatsApp)
        switch (notificationDTO.getType()) {
            case "SMS":
                sendSmsNotification(notificationDTO);
                break;

            case "Email":
                sendEmailNotification(notificationDTO);
                break;

            case "Push":
                sendPushNotification(notificationDTO);
                break;
            case "WhatsApp":
                sendWhatsAppNotification(notificationDTO);
                break;

            default:
                throw new IllegalArgumentException("Unknown notification type: " + notificationDTO.getType());
        }

        // Return the saved notification
        return notification;
    }


    //{
// 
//    "recipient": "customer90@",
//    "message": "Your order has been shipped",
//    "type": "SMS",
//    "timestamp": "2024-11-21T10:30:45",
//    "status": "Sent"
//  }



    // Fetch all notifications
    public List<Notification> getAllNotifications() {
        return notificationRepository.findAll();
    }

    // Get notification by ID
    public Optional<Notification> getNotificationById(Long id) {
        return notificationRepository.findById(id);
    }

}
