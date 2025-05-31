package com.Notification_Service.Controller;

import com.Notification_Service.Service.NotificationService;
import com.Notification_Service.DTO.NotificationDTO;
import com.Notification_Service.Entity.Notification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    @Autowired
    private NotificationService notificationService;
    
    @PostMapping("/send")
    public ResponseEntity<Notification> sendNotification(@RequestBody NotificationDTO notificationDTO) {
        Notification notification = notificationService.sendNotification(notificationDTO);
        return new ResponseEntity<>(notification, HttpStatus.CREATED);
    }
    //http://localhost:2006/notifications/send
//    {
//        "recipient": "customer10@example.com",
//        "message": "Your order is ready for pickup!",
//        "type": "Email",
//        "status": "Sent",
//        "restaurantName": "A Hotel"
//    }



    // Send SMS Notification
    @PostMapping("/sms")
    public ResponseEntity<Notification> sendSms(@RequestBody NotificationDTO notificationDTO) {
        Notification notification = notificationService.sendSmsNotification(notificationDTO);
        return new ResponseEntity<>(notification, HttpStatus.CREATED);
    }//http://localhost:2006/api/notifications/sms
//    {
//        "recipient": "+1234567890",
//        "message": "Your pizza order has been dispatched!",
//        "status": "Sent",
//        "restaurantName": "Pizza Hut"
//    }


    // Send Email Notification
    @PostMapping("/email")
    public ResponseEntity<Notification> sendEmail(@RequestBody NotificationDTO notificationDTO) {
        Notification notification = notificationService.sendEmailNotification(notificationDTO);
        return new ResponseEntity<>(notification, HttpStatus.CREATED);
    }
    //POST http://localhost:2006/notifications/email
//    {
//        "recipient": "+1234567890",
//        "message": "Your order will arrive soon on WhatsApp!",
//        "status": "Sent",
//        "restaurantName": "Pizza Hut"
//    }


    // Send Email Notification
    @PostMapping("/whatsapp")
    public ResponseEntity<Notification> sendWhatsAppNotification(@RequestBody NotificationDTO notificationDTO) {
        Notification notification = notificationService.sendWhatsAppNotification(notificationDTO);
        return new ResponseEntity<>(notification, HttpStatus.CREATED);
    }
    //http://localhost:2006/notifications/whatsapp
//    {
//        "recipient": "+1234567890",
//        "message": "Your order will arrive soon on WhatsApp!",
//        "status": "Sent",
//        "restaurantName": "Pizza Hut"
//    }


    // Send Push Notification
    @PostMapping("/push")
    public ResponseEntity<Notification> sendPush(@RequestBody NotificationDTO notificationDTO) {
        Notification notification = notificationService.sendPushNotification(notificationDTO);
        return new ResponseEntity<>(notification, HttpStatus.CREATED);
    }

    // Endpoint for customer to view notifications sent by a particular restaurant
    @GetMapping("/customer/{recipient}/restaurant/{restaurantName}")
    public ResponseEntity<List<Notification>> getNotificationsByRecipientAndRestaurant(
            @PathVariable String recipient, 
            @PathVariable String restaurantName) {

        List<Notification> notifications = notificationService.getNotificationsByRecipientAndRestaurant(recipient, restaurantName);
        
        if (notifications.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content if no notifications found
        }

        return new ResponseEntity<>(notifications, HttpStatus.OK); // 200 OK if notifications found
    }
    //http://localhost:2006/notifications/customer/customer100@example.com/restaurant/AHotel
   

    // Get all notifications
    @GetMapping("/all")
    public ResponseEntity<List<Notification>> getAllNotifications() {
        List<Notification> notifications = notificationService.getAllNotifications();
        return new ResponseEntity<>(notifications, HttpStatus.OK);
    }
    //http://localhost:2006/notifications/all

    // Get notification by ID
    @GetMapping("/{id}")
    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
        Optional<Notification> notification = notificationService.getNotificationById(id);
        return notification.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
 //   http://localhost:2006/notifications/1
}
