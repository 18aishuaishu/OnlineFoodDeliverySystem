package com.Admin_Service.DTO;


public class NotificationDTO {

    private String recipient;
    private String message;
    private String type; // SMS, Email, Push Notification

    public NotificationDTO() {}

    public NotificationDTO(String recipient, String message, String type) {
        this.recipient = recipient;
        this.message = message;
        this.type = type;
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
}
