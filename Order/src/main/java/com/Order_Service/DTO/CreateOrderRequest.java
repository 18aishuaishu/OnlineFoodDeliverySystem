package com.Order_Service.DTO;

import java.util.List;

import com.Order_Service.Entity.OrderItem;

public class CreateOrderRequest {
	

    private Long customerId;
    private Long restaurantId;
    private List<OrderItemRequest> orderItems; // This should be a list of order item data, not entities

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

    public List<OrderItemRequest> getOrderItems() {
        return orderItems;
    }

    public void setOrderItems(List<OrderItemRequest> orderItems) {
        this.orderItems = orderItems;
    }}
