package com.Customer_Service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

import com.Admin_Service.FeignClient.NotificationServiceFeignClient;
import com.Customer_Service.DTO.CreateOrderRequest;
import com.Customer_Service.DTO.PaymentDTO;
import com.Customer_Service.Entity.Customer;
import com.Customer_Service.Entity.MenuItem;
import com.Customer_Service.Entity.Notification;
import com.Customer_Service.Entity.Order;
import com.Customer_Service.Entity.Payment;
import com.Customer_Service.Entity.Restaurant;
import com.Customer_Service.Exception.CustomerNotFoundException;
import com.Customer_Service.FeignClient.NotificationClient;
import com.Customer_Service.FeignClient.OrderServiceClient;
import com.Customer_Service.FeignClient.PaymentClient;
import com.Customer_Service.FeignClient.RestaurantClient;
import com.Customer_Service.Reposiory.CustomerRepository;

import feign.FeignException;

import java.util.Optional;
@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;   
    @Autowired
    private RestaurantClient restaurantFeignClient;
     @Autowired
     private OrderServiceClient orderServiceClient;
     @Autowired
     private PaymentClient paymentServiceClient;
     @Autowired
     private NotificationClient notificationServiceClient;
   


    public Customer createCustomer(Customer customer) {
        return customerRepository.save(customer);
    }
    public void deleteCustomer(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        
        if (customer.isPresent()) {
            customerRepository.deleteById(id);  // Delete customer by ID
        } else {
            throw new CustomerNotFoundException("Customer not found with ID: " + id); // Throw exception if customer not found
        }
    }

    public Optional<Customer> getCustomer(Long id) {
        return customerRepository.findById(id);  // Find customer by ID from repository
    }

    public Optional<Customer> getCustomerByName(String name) {
        return customerRepository.findByName(name);
    }

    public Optional<Customer> getCustomerByEmail(String email) {
        return customerRepository.findByEmail(email);
    }

    public Customer updateCustomer(Long id, Customer customer) {
        if (customerRepository.existsById(id)) {
            customer.setId(id);
            return customerRepository.save(customer);
        } else {
            throw new CustomerNotFoundException("Customer not found with ID: " + id);
        }
    }

    public List<Customer> getAllCustomers() {
        return customerRepository.findAll();  // Fetches all customers from the database
    }
    //Restaurant service feign  
    public List<Restaurant> getRestaurants() {
        return restaurantFeignClient.getAllRestaurants();
    }

    // Fetch menu items for a specific restaurant
    public List<MenuItem> getMenu(Long restaurantId) {
        return restaurantFeignClient.getMenuItems(restaurantId);
    }

    // Create an order
    public Order createOrder(CreateOrderRequest orderRequest) {
        return orderServiceClient.createOrder(orderRequest);
    }
    //payment
    public Payment makePayment(Long customerId, PaymentDTO paymentDTO) {
        paymentDTO.setCustomerId(customerId);  // Associate customerId with the PaymentDTO
      
        return paymentServiceClient.processPayment(paymentDTO);  // Make the payment through the FeignClient
    }
    // Method to fetch notifications for a customer from a specific restaurant
    public List<Notification> getNotificationsForCustomer(String recipient, String restaurantName) {
        return notificationServiceClient.getNotificationsByRecipientAndRestaurant(recipient, restaurantName);
    }
    public List<Order> getOrdersByCustomerAndRestaurant(Long customerId, Long restaurantId) {
        // Fetch orders from OrderService via Feign Client
        List<Order> orders = orderServiceClient.getOrdersByCustomerAndRestaurant(customerId, restaurantId)
                                                .getBody();  // Extracting the body of the response

        // If no orders are found, we can return an empty list or throw an exception depending on business rules
        if (orders == null || orders.isEmpty()) {
            return orders; // You can also choose to throw an exception like "No Orders Found"
        }

        // You can also filter the orders or add business rules here, for example:
        // Example: Only return orders with status "DELIVERED"
        // return orders.stream().filter(order -> "DELIVERED".equals(order.getOrderStatus()))
        //              .collect(Collectors.toList());

        return orders;  // Return the fetched orders
    }

    
//-----------------customer service-------------------
    //get the order details by id
    public Order getOrderById(Long orderId) {
        ResponseEntity<Order> response = orderServiceClient.getOrderById(orderId);
        if (response.getStatusCode().is2xxSuccessful()) {
            return response.getBody();
        } else {
            // Handle error (order not found, etc.)
            throw new RuntimeException("Order not found with ID: " + orderId);
        }
    }

}
        