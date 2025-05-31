package com.Customer_Service.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.Customer_Service.DTO.CreateOrderRequest;
import com.Customer_Service.DTO.PaymentDTO;
import com.Customer_Service.Entity.Customer;
import com.Customer_Service.Entity.MenuItem;
import com.Customer_Service.Entity.Notification;
import com.Customer_Service.Entity.Order;
import com.Customer_Service.Entity.Payment;
import com.Customer_Service.Entity.Restaurant;
import com.Customer_Service.Exception.CustomerNotFoundException;
import com.Customer_Service.FeignClient.OrderServiceClient;
import com.Customer_Service.FeignClient.RestaurantClient;
import com.Customer_Service.Service.CustomerService;

@RestController
@RequestMapping("/customers")
@CrossOrigin(origins = "http://localhost:4200")

public class CustomerController {

    @Autowired
    private CustomerService customerService;
    @Autowired
    private RestaurantClient restaurantClient;

    @Autowired
    private OrderServiceClient orderServiceClient;



    @GetMapping("/getall")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = customerService.getAllCustomers();  // Call the service method
        if (customers.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Return HTTP 204 if no customers found
        }
        return new ResponseEntity<>(customers, HttpStatus.OK);  // Return HTTP 200 with the customer list
    }
    //http://localhost:2001/customers/getall

    // Create Customer
    @PostMapping("/post")
    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
        Customer createdCustomer = customerService.createCustomer(customer);
        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);
    }

    //http://localhost:2001/customers/post
// {
//        
//        "name": "ghill",
//        "email": "ag@gmail.com",
//        "phone": "1876",
//        "address": "Madurai"
//    }

    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Customer> getCustomerById(@PathVariable Long id) {
        Optional<Customer> customer = customerService.getCustomer(id);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    //http://localhost:2001/customers/getbyid/2

    // Get Customer by Name
    @GetMapping("/name/{name}")
    public ResponseEntity<Customer> getCustomerByName(@PathVariable String name) {
        Optional<Customer> customer = customerService.getCustomerByName(name);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    //http://localhost:2001/customers/name/Aishu

    // Get Customer by Email
    @GetMapping("/email/{email}")
    public ResponseEntity<Customer> getCustomerByEmail(@PathVariable String email) {
        Optional<Customer> customer = customerService.getCustomerByEmail(email);
        return customer.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    //http://localhost:2001/customers/email/jj@gmail.com

    // Update Customer Info
    @PutMapping("/update/{id}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long id, @RequestBody Customer customer) {
        Customer updatedCustomer = customerService.updateCustomer(id, customer);
        return updatedCustomer != null
                ? new ResponseEntity<>(updatedCustomer, HttpStatus.OK)
                : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    //http://localhost:2001/customers/update/2?name=ghill&email=g@gmail.com&phone=9909&address=Madurai
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCustomer(@PathVariable Long id) {
        try {
            // Call service method to delete the customer
            customerService.deleteCustomer(id);
            return ResponseEntity.ok("Customer deleted successfully");
        } catch (CustomerNotFoundException e) {
            // Handle case where customer is not found
            return ResponseEntity.status(404).body("Customer not found with ID: " + id);
        } catch (Exception e) {
            // Handle other potential exceptions
            return ResponseEntity.status(500).body("An error occurred while deleting the customer");
        }
    }
   
    
   //Restaurant feign
    // Get list of all restaurants
    ///-------------------------------restaurants----------------------
    @GetMapping("/restaurants")
    public List<Restaurant> getRestaurants() {
        return customerService.getRestaurants();
    }
    //http://localhost:2001/customers/restaurants

    // Get menu items for a specific restaurant
    @GetMapping("/restaurants/{restaurantId}/menu")
    public List<MenuItem> getMenu(@PathVariable Long restaurantId) {
        return customerService.getMenu(restaurantId);
    }
//http://localhost:2001/customers/restaurants/2/menu
   //------------------------order-------------------------------
    
    // Create an order
    @PostMapping("/orders/createorder")
    public Order createOrder(@RequestBody CreateOrderRequest orderRequest) {
        return customerService.createOrder(orderRequest);
    }
//    {
//     	  "customerId": 100,
//     	  "restaurantId": 5,
//
//     	  "orderItems": [
//     	    {
//     	      "menuItemId": 301,
//     	      "quantity": 2,
//     	      "costPerItem": 5.0,
//     	      "totalCost": 10.0
//     	    },
//     	    {
//     	      "menuItemId": 302,
//     	      "quantity": 3,
//     	      "costPerItem": 5.5,
//     	      "totalCost": 16.5
//     	    }
//     	  ]
//     	}
    // Fetch order details for a specific customer by order ID
    @GetMapping("/order/{orderId}")
    public ResponseEntity<Order> getOrder(@PathVariable Long orderId) {
        Order order = customerService.getOrderById(orderId);
        return ResponseEntity.ok(order);  // Return the order details as a response
    }
    //http://localhost:2001/customers/order/1
    
    @GetMapping("/orders/{customerId}/orders/{restaurantId}")
    public ResponseEntity<List<Order>> getOrdersByCustomerAndRestaurant(
            @PathVariable Long customerId,
            @PathVariable Long restaurantId) {

        // Call the Feign client to fetch orders from the Order Service
        ResponseEntity<List<Order>> ordersResponse = orderServiceClient.getOrdersByCustomerAndRestaurant(customerId, restaurantId);

        // If no orders found, return 404 Not Found
        if (ordersResponse.getBody() == null || ordersResponse.getBody().isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Return the list of orders with 200 OK response
        return ResponseEntity.ok(ordersResponse.getBody());
    }
    //http://localhost:2001/customers/orders/100/orders/5

    //----------------------payment---------------------------
    
 // Endpoint for customers to make a payment initially status was set to be success
    @PostMapping("/payment/{customerId}/make-payment")
    public ResponseEntity<Payment> makePayment(@PathVariable Long customerId, 
                                               @RequestBody PaymentDTO paymentDTO) {
        try {
            // Call CustomerService to make the payment via PaymentService
            Payment payment = customerService.makePayment(customerId, paymentDTO);
            return new ResponseEntity<>(payment, HttpStatus.CREATED);  // Return 201 if payment is successful
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Return 400 if error occurs
        }
    }
    //http://localhost:2001/customers/payment/1/make-payment
//    {
//  	  "orderId": 12345,
//  	  "paymentMethod": "Debit Card",
//  	  "totalCost": 250.0,
//  	  "paymentDate": "2024-11-23T10:00:00"
//  	 
//  	}
//----------------------notification-----------------------------------
  
    @GetMapping("/{recipient}/restaurant/{restaurantName}")
    public ResponseEntity<List<Notification>> getNotifications(
            @PathVariable String recipient,
            @PathVariable String restaurantName) {
        
        List<Notification> notifications = customerService.getNotificationsForCustomer(recipient, restaurantName);
        
        if (notifications.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content if no notifications found
        }
        
        return ResponseEntity.ok(notifications); // 200 OK if notifications are found
    }
    
    //http://localhost:2001/customers/customer123@domain.com/restaurant/PizzaPalace
    
   


   }
