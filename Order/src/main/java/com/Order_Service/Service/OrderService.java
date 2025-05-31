package com.Order_Service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.Order_Service.DTO.CreateOrderItemRequest;
import com.Order_Service.DTO.OrderItemRequest;
import com.Order_Service.Entity.MenuItem;
import com.Order_Service.Entity.Order;
import com.Order_Service.Entity.OrderItem;
import com.Order_Service.Entity.Payment;
import com.Order_Service.FeignClient.PaymentFeignClient;
import com.Order_Service.FeignClient.RestaurantServiceClient;
import com.Order_Service.Repository.OrderItemRepository;
import com.Order_Service.Repository.OrderRepository;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


import java.util.List;

@Service
public class OrderService {

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private RestaurantServiceClient restaurantServiceClient;
    
    @Autowired
    private PaymentFeignClient paymentFeignClient;
    
    
    public List<Order> getAllOrders() {
        // Fetch all orders from the database
        return orderRepository.findAll();
    }
    public Order getOrderById(Long orderId) {
     return orderRepository.findById(orderId)
              .orElseThrow(() -> new IllegalArgumentException("Order not found with ID: " + orderId));
  }  // Update order status
    public Order updateOrderStatus(Long id, String status) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new RuntimeException("Order not found"));
        order.setStatus(status);
        return orderRepository.save(order);
    }
    public List<Order> getOrdersByCustomerAndRestaurant(Long customerId, Long restaurantId) {
        return orderRepository.findByCustomerIdAndRestaurantId(customerId, restaurantId);
    }

//    public Order createOrder(Long customerId, Long restaurantId, List<OrderItem> orderItems) {
//        // Fetch menu items from the Restaurant Service
//        List<MenuItem> menuItems = restaurantServiceClient.getMenuItems(restaurantId);
//
//        // Validate the ordered items (for example, check if they exist in the restaurant's menu)
//        for (OrderItem orderItem : orderItems) {
//            boolean itemExists = menuItems.stream()
//                    .anyMatch(menuItem -> menuItem.getId().equals(orderItem.getMenuItemId()));
//            if (!itemExists) {
//                throw new IllegalArgumentException("Menu item not found in restaurant's menu.");
//            }
//        }
//
//        // Calculate total amount
//        double totalAmount = orderItems.stream()
//                .mapToDouble(item -> item.getTotalCost())
//                .sum();
//
//        // Create a new order
//        Order order = new Order();
//        order.setCustomerId(customerId);
//        order.setRestaurantId(restaurantId);
//        order.setTotalAmount(totalAmount);
//        order.setOrderItems(orderItems);
//
//        // Save the order
//        return orderRepository.save(order);
//    }
//
//    @Transactional  // Ensures that both order creation and payment are processed atomically
//    public Order createOrder(Order order) {
//        // Save the order to the database
//        Order savedOrder = orderRepository.save(order);
//        
//        // After saving the order, automatically create a payment for it
//        Payment payment = new Payment();
//        payment.setOrderId(savedOrder.getId());
//        payment.setPaymentStatus("Pending");  // Default status, can be updated later
//        payment.setTotalCost(savedOrder.getTotalAmount());
//        payment.setPaymentDate(new Date());
//        payment.setCustomerId(savedOrder.getCustomerId());  // Link to the customer who made the order
//        
//        // Call Payment Service to create a payment record
//        paymentFeignClient.createPayment(payment);  // This makes a REST call to the Payment Service
//
//        return savedOrder;
//    }
 // Method to create an order
    
    
    @Transactional  // Ensure atomicity when saving both Order and OrderItems
    public Order createOrder(Long customerId, Long restaurantId, List<OrderItemRequest> list) {
        // Your existing logic for creating an order
        Order order = new Order();
        order.setCustomerId(customerId);
        order.setRestaurantId(restaurantId);
        order.setStatus("Pending");

        double totalAmount = 0.0;
        List<OrderItem> orderItemList = new ArrayList<>();

        for (OrderItemRequest item : list) {
            OrderItem orderItem = new OrderItem();
            orderItem.setMenuItemId(item.getMenuItemId());
            orderItem.setQuantity(item.getQuantity());
            orderItem.setCostPerItem(item.getCostPerItem());
            orderItem.setTotalCost(item.getTotalCost());
            orderItem.setOrder(order);  // Link to the order
            orderItemList.add(orderItem);
            totalAmount += item.getTotalCost();
        }

        order.setOrderItems(orderItemList);
        order.setTotalAmount(totalAmount);

        return orderRepository.save(order);
    }
//    public Order createOrder(Long customerId, Long restaurantId, List<OrderItemRequest> orderItemRequests) {
//        // Get menu items from Restaurant Service (optional)
//        List<MenuItem> menuItems = restaurantServiceClient.getMenuItems(restaurantId);
//
//        // Calculate total cost from the order items
//        double totalCost = 0;
//        List<OrderItem> orderItems = new ArrayList<>();
//
//        for (OrderItemRequest itemRequest : orderItemRequests) {
//            // Create OrderItem from OrderItemRequest
//            OrderItem orderItem = new OrderItem();
//            orderItem.setMenuItemId(itemRequest.getMenuItemId());
//            orderItem.setQuantity(itemRequest.getQuantity());
//            orderItem.setCostPerItem(itemRequest.getCostPerItem());
//            orderItem.setTotalCost(itemRequest.getTotalCost());
//
//            // Add to the list of orderItems
//            orderItems.add(orderItem);
//
//            // Add up the total cost
//            totalCost += itemRequest.getTotalCost();
//        }
//
//        // Create Order object
//        Order order = new Order();
//        order.setCustomerId(customerId);
//        order.setRestaurantId(restaurantId);
//        order.setStatus("Pending");
//        order.setTotalAmount(totalCost);
//        order.setOrderItems(orderItems);
//
//        // Set the order reference in each order item
//        for (OrderItem orderItem : orderItems) {
//            orderItem.setOrder(order);
//        }
//
//        // Save the order and its associated order items
//        order = orderRepository.save(order);
//
//        return order; // Return the saved order with its order items
//    }
    // Method to delete an order by ID
    public void deleteOrderById(Long id) {
        // Check if the order exists before attempting to delete
        if (orderRepository.existsById(id)) {
            orderRepository.deleteById(id); // Delete the order from the database
        } else {
            throw new IllegalArgumentException("Order with id " + id + " not found.");
        }
    }
    public List<Order> getOrdersByCustomerId(Long customerId) {
        return orderRepository.findByCustomerId(customerId);
    }
    

}
