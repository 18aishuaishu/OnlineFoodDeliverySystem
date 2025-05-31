package com.Order_Service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Order_Service.Entity.OrderItem;

public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
}