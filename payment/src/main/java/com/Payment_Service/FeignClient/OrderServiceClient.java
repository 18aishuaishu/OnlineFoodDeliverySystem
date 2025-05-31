package com.Payment_Service.FeignClient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Payment_Service.Entity.Order;

@FeignClient(name = "order", url = "http://localhost:2009/orders")
public interface OrderServiceClient {

    @GetMapping("/{orderId}")
    Order getOrderById(@PathVariable("orderId") Long orderId);
    //@GetMapping("/orders/{id}")
   // Order getOrderById(@PathVariable("id") Long id);
    
}