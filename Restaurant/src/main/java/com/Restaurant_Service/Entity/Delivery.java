package com.Restaurant_Service.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.time.LocalDateTime;


public class Delivery {

    private Long id;

    private Long orderId;

   
    private String status; // e.g., "Pending", "Shipped", "Delivered"

    private LocalDateTime deliveryDate;

    private String trackingNumber;

    // Constructors, Getters, and Setters
    public Delivery() {
    }

    public Delivery(Long orderId, String status, LocalDateTime deliveryDate, String trackingNumber) {
        this.orderId = orderId;
        this.status = status;
        this.deliveryDate = deliveryDate;
        this.trackingNumber = trackingNumber;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(LocalDateTime deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public String getTrackingNumber() {
        return trackingNumber;
    }

    public void setTrackingNumber(String trackingNumber) {
        this.trackingNumber = trackingNumber;
    }
}
