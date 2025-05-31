package com.Admin_Service.Entity;


import java.util.Date;

public class Payment {

    private Long id;   

    private Long orderId;  // Foreign key to Order (you can add a relationship later)
    private String paymentMethod;  // e.g., Credit Card, PayPal, etc.
    private String paymentStatus;  // e.g., Pending, Success, Failed
    private Date paymentDate;
    private Long customerId;  // This will be used to identify the customer

    public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
    private double totalCost;  // Total cost for all paintings purchased (amount * totalPaintings)
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
	public String getPaymentMethod() {
		return paymentMethod;
	}
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
	}
	public Date getPaymentDate() {
		return paymentDate;
	}
	public void setPaymentDate(Date paymentDate) {
		this.paymentDate = paymentDate;
	

	}
	public double getTotalCost() {
		return totalCost;
	}
	public void setTotalCost(double totalCost) {
		this.totalCost = totalCost;
	}

}
