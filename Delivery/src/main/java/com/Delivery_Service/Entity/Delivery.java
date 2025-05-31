package com.Delivery_Service.Entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import java.time.LocalDateTime;

@Entity
@Table(name = "deliveries")
public class Delivery {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long orderId;
    @Column(name = "customer_id") // use customerId as a foreign key reference

    private Long customerId;  // Store only the customerId as a foreign key (no need for JPA relationship)


    @Column(nullable = false)
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

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	
}
