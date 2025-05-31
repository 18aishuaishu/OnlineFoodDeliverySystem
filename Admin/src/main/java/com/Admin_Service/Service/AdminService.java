package com.Admin_Service.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.client.RestTemplate;

import com.Admin_Service.DTO.CreateOrderRequest;
import com.Admin_Service.DTO.NotificationDTO;
import com.Admin_Service.DTO.PaymentDTO;
import com.Admin_Service.Entity.Admin;
import com.Admin_Service.Entity.Customer;
import com.Admin_Service.Entity.Delivery;
import com.Admin_Service.Entity.MenuItem;
import com.Admin_Service.Entity.Notification;
import com.Admin_Service.Entity.Order;
import com.Admin_Service.Entity.Payment;
import com.Admin_Service.Entity.Restaurant;
import com.Admin_Service.FeignClient.CustomerServiceFeignClient;
import com.Admin_Service.FeignClient.DeliveryServiceFeignClient;
import com.Admin_Service.FeignClient.NotificationServiceFeignClient;
import com.Admin_Service.FeignClient.OrderServiceFeignClient;
import com.Admin_Service.FeignClient.PaymentServiceFeignClient;
import com.Admin_Service.FeignClient.RestaurantServiceFeignClient;
import com.Admin_Service.Repository.AdminRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;



@Service
public class AdminService {

    @Autowired
    private RestTemplate restTemplate;

    @Autowired
    private OrderServiceFeignClient orderServiceFeignClient;
    @Autowired
    private PaymentServiceFeignClient paymentServiceFeignClient;
    @Autowired
   	private AdminRepository adminrepository;
    @Autowired
    private CustomerServiceFeignClient customerServiceFeignClient;
    @Autowired
    private RestaurantServiceFeignClient restaurantServiceFeignClient;
    @Autowired
    private DeliveryServiceFeignClient deliveryServiceFeignClient;
    @Autowired
    private NotificationServiceFeignClient notificationServiceFeignClient;
    

    public Admin getById(Long id) {
        return adminrepository.findById(id).orElse(null);
    }
    //--------------------------Delivery service------------------------
    //Delivery getall and update the details
    public List<Delivery> getAllDeliveries() {
    return deliveryServiceFeignClient.getAllDeliveries();
}

// Update the status of a delivery
public Delivery updateDeliveryStatus(Long id, String status) {
    return deliveryServiceFeignClient.updateDeliveryStatus(id, status);
}

// Get a delivery by ID
public Optional<Delivery> getDeliveryById(Long id) {
    return deliveryServiceFeignClient.getDeliveryById(id); // Calling FeignClient method
}

// Delete a delivery by ID using FeignClient
public void deleteDelivery(Long id) {
    deliveryServiceFeignClient.deleteDelivery(id); // Calling FeignClient method
}

///-------------Orders----------------------


// Get all orders
public List<Order> getAllOrders() {
    return orderServiceFeignClient.getAllOrders();
}

// Get order by ID
public Order getOrderById(Long orderId) {
    return orderServiceFeignClient.getOrderById(orderId);
}

// Create a new order
public Order createOrder(CreateOrderRequest createOrderRequest) {
    return orderServiceFeignClient.createOrder(createOrderRequest);
}

// Update order status
public Order updateOrderStatus(Long id, String status) {
    return orderServiceFeignClient.updateOrderStatus(id, status);
}

// Delete an order
public void deleteOrder(Long orderId) {
    orderServiceFeignClient.deleteOrder(orderId);
}
//----------------customer service-----------------
// Fetch all customers using Feign Client
public List<Customer> getAllCustomers() {
    return customerServiceFeignClient.getAllCustomers();
}

// Fetch customer by ID using Feign Client
public Customer getCustomerById(Long customerId) {
    return customerServiceFeignClient.getCustomerById(customerId);
}

// Create a new customer using Feign Client
public Customer createCustomer(Customer customer) {
    return customerServiceFeignClient.createCustomer(customer);
}

// Update customer information using Feign Client
public Customer updateCustomer(Long customerId, Customer customer) {
    return customerServiceFeignClient.updateCustomer(customerId, customer);
}

// Delete a customer using Feign Client
public void deleteCustomer(Long customerId) {
	customerServiceFeignClient.deleteCustomer(customerId);
}
//restaurant----------------------------------------
// Get all restaurants
public List<Restaurant> getAllRestaurants() {
    return restaurantServiceFeignClient.getAllRestaurants();
}

// Get restaurant by ID
public Restaurant getRestaurantById(Long id) {
    return restaurantServiceFeignClient.getRestaurantById(id);
}
// Add a new restaurant
public Restaurant addRestaurant(Restaurant restaurant) {
    return restaurantServiceFeignClient.addRestaurant(restaurant);
}

// Add a menu item to a restaurant
public MenuItem addMenuItem(Long restaurantId, MenuItem menuItem) {
    return restaurantServiceFeignClient.addMenuItem(restaurantId, menuItem);
}

// Get all menu items of a restaurant
public List<MenuItem> getMenuItems(Long restaurantId) {
    return restaurantServiceFeignClient.getMenuItems(restaurantId);
}
   
//------------------payment service---------------------------------------------
   
    // Get all payments
    public List<Payment> getAllPayments() {
        return paymentServiceFeignClient.getAllPayments();
    }

    // Get payment by ID
    public Payment getPaymentById(Long id) {
        return paymentServiceFeignClient.getById(id);
    }

    // Process payment
    public Payment processPayment(PaymentDTO paymentDTO) {
        return paymentServiceFeignClient.processPayment(paymentDTO);
    }
//---------------notification service ------------------------------------------


    // Send a generic notification (can be used for any type of notification)
    public Notification sendNotification(NotificationDTO notificationDTO) {
        return notificationServiceFeignClient.sendNotification(notificationDTO);
    }

    // Send SMS Notification
    public Notification sendSms(NotificationDTO notificationDTO) {
        return notificationServiceFeignClient.sendSms(notificationDTO);
    }

    // Send Email Notification
    public Notification sendEmail(NotificationDTO notificationDTO) {
        return notificationServiceFeignClient.sendEmail(notificationDTO);
    }

    // Send WhatsApp Notification
    public Notification sendWhatsAppNotification(NotificationDTO notificationDTO) {
        return notificationServiceFeignClient.sendWhatsAppNotification(notificationDTO);
    }

    // Send Push Notification
    public Notification sendPushNotification(NotificationDTO notificationDTO) {
        return notificationServiceFeignClient.sendPushNotification(notificationDTO);
    }

    // Fetch all notifications
    public List<Notification> getAllNotifications() {
        return notificationServiceFeignClient.getAllNotifications();
    }

    // Fetch notification by ID
    public Notification getNotificationById(Long id) {
        return notificationServiceFeignClient.getNotificationById(id);
    }
    
}