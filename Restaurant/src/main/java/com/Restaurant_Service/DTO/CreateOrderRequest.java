package com.Restaurant_Service.DTO;

import java.util.List;

import com.Restaurant_Service.Entity.OrderItem;


public class CreateOrderRequest {
    private Long customerId;
    private Long restaurantId;
    private List<OrderItem> orderItems;
	public Long getCustomerId() {
		return customerId;
	}
	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}
	public Long getRestaurantId() {
		return restaurantId;
	}
	public void setRestaurantId(Long restaurantId) {
		this.restaurantId = restaurantId;
	}
	public List<OrderItem> getOrderItems() {
		return orderItems;
	}
	public void setOrderItems(List<OrderItem> orderItems) {
		this.orderItems = orderItems;
	}

    // Getters and setters
}
