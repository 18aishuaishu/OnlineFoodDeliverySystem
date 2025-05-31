package com.Customer_Service.FeignClient;



import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import com.Customer_Service.DTO.CreateOrderRequest;
import com.Customer_Service.Entity.Order;



@FeignClient(name = "order",url = "http://localhost:2009/orders")
public interface OrderServiceClient {

	// Create a new order
    @PostMapping("/post")
    public Order createOrder(@RequestBody CreateOrderRequest createOrderRequest);
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id);

    // Method to get all orders by customerId and restaurantId
    @GetMapping("/restaurant/{restaurantId}/customer/{customerId}")
    ResponseEntity<List<Order>> getOrdersByCustomerAndRestaurant(
        @PathVariable("customerId") Long customerId, 
        @PathVariable("restaurantId") Long restaurantId
    );
    
}
