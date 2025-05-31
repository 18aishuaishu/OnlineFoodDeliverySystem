package com.Order_Service.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Order_Service.Entity.Order;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByCustomerId(Long customerId);
    List<Order> findByCustomerIdAndRestaurantId(Long customerId, Long restaurantId);

}

