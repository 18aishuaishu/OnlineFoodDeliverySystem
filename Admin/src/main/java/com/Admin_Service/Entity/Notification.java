package com.Admin_Service.Entity;
import java.time.LocalDateTime;
public class Notification {

    
    private Long id;

    private String recipient;
    private String message;
    private String type; // SMS, Email, Push Notification
    private String status; // Sent, Failed, Pending

    private LocalDateTime timestamp;

    // Constructor, Getters, Setters
    public Notification() {}

    public Notification(String recipient, String message, String type, String status) {
        this.recipient = recipient;
        this.message = message;
        this.type = type;
        this.status = status;
        this.timestamp = LocalDateTime.now();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(LocalDateTime timestamp) {
        this.timestamp = timestamp;
    }
}
