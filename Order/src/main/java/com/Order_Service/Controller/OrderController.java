package com.Order_Service.Controller;

import com.Order_Service.DTO.CreateOrderRequest;
import com.Order_Service.Entity.Order;
import com.Order_Service.Repository.OrderRepository;
import com.Order_Service.Service.OrderService;

import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/orders")
@CrossOrigin(origins = "http://localhost:4200")  // Allow CORS from Angular app
public class OrderController {

    @Autowired
    private OrderService orderService;
    @Autowired
    private OrderRepository orderRepository;
    // Get all orders (optional, if needed)
    
    @GetMapping("/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomer(@PathVariable Long customerId) {
        List<Order> orders = orderService.getOrdersByCustomerId(customerId);

        if (orders.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList());
        }
        return ResponseEntity.ok(orders);
    }
//dont change getORDERSBYCUSTOMER ->helps to get order details in customer component
    
    
    
    
    
    @PostMapping("/checkout")
    public ResponseEntity<Order> checkout(@RequestBody CreateOrderRequest createOrderRequest) {
        try {
            // Process the checkout, create order, and save in database
            Order order = orderService.createOrder(
                    createOrderRequest.getCustomerId(),
                    createOrderRequest.getRestaurantId(),
                    createOrderRequest.getOrderItems()
            );
            
            // Check if order is successfully created and contains an ID
            if (order != null && order.getId() != null) {
                return ResponseEntity.status(HttpStatus.CREATED).body(order);
            } else {
                // If no order ID is found
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(null); // Return an error response
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


    // Get all orders
    @GetMapping("/getall")
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }
    //http://localhost:2009/orders/getall
  
    
    // Create a new order

    @PostMapping("/post")
    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
        // Create order using the service
        Order order = orderService.createOrder(
            createOrderRequest.getCustomerId(),
            createOrderRequest.getRestaurantId(),
            createOrderRequest.getOrderItems()
        );

        // Return the order with its associated order items
        return ResponseEntity.status(HttpStatus.CREATED).body(order);
    }
    //http://localhost:2009/orders/post
//    {
//    	  "customerId": 100,
//    	  "restaurantId": 5,
//
//    	  "orderItems": [
//    	    {
//    	      "menuItemId": 301,
//    	      "quantity": 2,
//    	      "costPerItem": 5.0,
//    	      "totalCost": 10.0
//    	    },
//    	    {
//    	      "menuItemId": 302,
//    	      "quantity": 3,
//    	      "costPerItem": 5.5,
//    	      "totalCost": 16.5
//    	    }
//    	  ]
//    	}

//    @PostMapping("/checkout")
//    public ResponseEntity<Order> checkout(@RequestBody CreateOrderRequest createOrderRequest) {
//        try {
//            // Process the checkout, create order, and save in database
//            Order order = orderService.createOrder(
//                    createOrderRequest.getCustomerId(),
//                    createOrderRequest.getRestaurantId(),
//                    createOrderRequest.getOrderItems()
//            );
//            return ResponseEntity.status(HttpStatus.CREATED).body(order);
//        } catch (Exception e) {
//            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
//        }
//    }
    @GetMapping("/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
        try {
            Order order = orderService.getOrderById(id);
            if (order == null) {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            // Log the response to ensure orderItems are included
            System.out.println("Order with items: " + order);
            return new ResponseEntity<>(order, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/getbyid/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
        Order order = orderService.getOrderById(orderId);
        return ResponseEntity.ok(order);
    }


    
//    @GetMapping("/getbyid/{id}")
//    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
//        try {
//            // Get the order using the service
//            Order order = orderService.getOrderById(id);
//            return new ResponseEntity<>(order, HttpStatus.OK); // 200 OK if order is found
//        } catch (IllegalArgumentException e) {
//            // If order is not found, return 404 Not Found
//            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
//        }
//    }
    //http://localhost:2009/orders/getbyid/1
    
    
    @PutMapping("/{id}/status")
    public Order updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        return orderService.updateOrderStatus(id, status);
    }

//http://localhost:2009/orders/2/status?status=Completed

// Endpoint to delete an order by ID
@DeleteMapping("/{id}")
public ResponseEntity<String> deleteOrder(@PathVariable Long id) {
    try {
        orderService.deleteOrderById(id);  // Delete the order
        return new ResponseEntity<>("Order with id " + id + " deleted successfully.", HttpStatus.NO_CONTENT);
    } catch (IllegalArgumentException e) {
        return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND); // If order not found
    }
}
//Endpoint for restaurant to view orders by customerId and restaurantId
// Fetch orders by customerId and restaurantId
@GetMapping("/restaurant/{restaurantId}/customer/{customerId}")
public ResponseEntity<List<Order>> getOrdersByCustomerAndRestaurant(
        @PathVariable Long customerId,
        @PathVariable Long restaurantId) {

    List<Order> orders = orderService.getOrdersByCustomerAndRestaurant(customerId, restaurantId);

    if (orders.isEmpty()) {
        return ResponseEntity.notFound().build();
    }

    // Return the list of orders with orderDate and totalCost
    return ResponseEntity.ok(orders);
}

//http://localhost:2009/orders/restaurant/5/customer/100                      
}