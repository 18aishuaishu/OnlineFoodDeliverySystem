package com.Restaurant_Service.DTO;


public class NotificationDTO {

    private String recipient;
    private String message;
    private String type; // SMS, Email, Push Notification
    private String status; // Sent, Failed, Pending
    private String restaurantName;    // The name or identifier of the restaurant sending the notification

    public String getRestaurantName() {
		return restaurantName;
	}

	public void setRestaurantName(String restaurantName) {
		this.restaurantName = restaurantName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public NotificationDTO() {}

    public NotificationDTO(String recipient, String message, String type, String status, String restaurantName) {
        this.recipient = recipient;
        this.message = message;
        this.type = type;
        this.status=status;
        this.restaurantName=restaurantName;
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
