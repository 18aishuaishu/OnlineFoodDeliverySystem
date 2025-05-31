package com.Admin_Service.Controller;
import com.Admin_Service.DTO.CreateOrderRequest;
import com.Admin_Service.DTO.NotificationDTO;
import com.Admin_Service.DTO.PaymentDTO;
import com.Admin_Service.Entity.Customer;
import com.Admin_Service.Entity.Delivery;
import com.Admin_Service.Entity.MenuItem;
import com.Admin_Service.Entity.Notification;
import com.Admin_Service.Entity.Order;
import com.Admin_Service.Entity.Payment;
import com.Admin_Service.Entity.Restaurant;
import com.Admin_Service.Service.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/admin")
public class AdminController {
	  @Autowired
	    private AdminService adminService;

	  
//	  @Autowired
//	    private JwtTokenProvider jwtTokenProvider;
//
//	    @PostMapping("/login")
//	    public String login(@RequestBody AdminLoginRequest loginRequest) {
//	        var admin = adminService.authenticate(loginRequest.getUsername(), loginRequest.getPassword());
//	        if (admin != null) {
//	            return jwtTokenProvider.generateToken(admin.getUsername());
//	        } else {
//	            throw new RuntimeException("Authentication failed");
//	        }
//	    }
//	  
	  
	  
	  

	    //deliveries service
	    //getall and update
	    @GetMapping("/delivery/getall")
	    public ResponseEntity<List<Delivery>> getAllDeliveries() {
	        List<Delivery> deliveries = adminService.getAllDeliveries();
	        if (deliveries.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // HTTP 204 if no deliveries found
	        }
	        return new ResponseEntity<>(deliveries, HttpStatus.OK); // HTTP 200 with deliveries
	    }
	    //http://localhost:2000/admin/delivery/1

	    // Update the status of a delivery
	    @PutMapping("/delivery/{id}/status")
	    public ResponseEntity<Delivery> updateDeliveryStatus(@PathVariable Long id, @RequestParam String status) {
	        try {
	            Delivery updatedDelivery = adminService.updateDeliveryStatus(id, status);
	            return new ResponseEntity<>(updatedDelivery, HttpStatus.OK); // HTTP 200 with updated delivery
	        } catch (RuntimeException e) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // HTTP 404 if delivery not found
	        }
	    }
	    //http://localhost:2000/admin/delivery/3/status?status=SHIPPED
	    
	    @GetMapping("/delivery/getbyid/{id}")
	    public ResponseEntity<Delivery> getDeliveryById(@PathVariable Long id) {
	        Optional<Delivery> delivery = adminService.getDeliveryById(id);

	        if (delivery.isPresent()) {
	            return new ResponseEntity<>(delivery.get(), HttpStatus.OK); // Return the delivery data with HTTP 200
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Delivery not found, return HTTP 404
	        }
	    }
	    //http://localhost:2000/admin/delivery/getbyid/1

	    // Delete a delivery by ID
	    @DeleteMapping("/delivery/delete/{id}")
	    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
	        try {
	            adminService.deleteDelivery(id); // Delete the delivery
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Successfully deleted, HTTP 204
	        } catch (RuntimeException e) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Delivery not found, HTTP 404
	        }
	    }
	    //http://localhost:2000/admin/delivery/delete/4
	    //-----------------------Order controller-----------------
	 // Get all orders
	    
	       @GetMapping("/order/getall")
	    public ResponseEntity<List<Order>> getAllOrders() {
	        List<Order> orders = adminService.getAllOrders();
	        if (orders.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 if no orders
	        }
	        return new ResponseEntity<>(orders, HttpStatus.OK); // Return 200 with orders list
	    }
	       //http://localhost:2000/admin/order/getall

	    // Get order by ID
	    @GetMapping("/order/getbyid/{id}")
	    public ResponseEntity<Order> getOrderById(@PathVariable("id") Long id) {
	        Order order = adminService.getOrderById(id);
	        if (order != null) {
	            return new ResponseEntity<>(order, HttpStatus.OK); // Return 200 with the order
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if order is not found
	        }
	    }
	    //http://localhost:2000/admin/order/getbyid/2

	 // AdminController.java

	    @PostMapping("/order/post")
	    public ResponseEntity<Order> createOrder(@RequestBody CreateOrderRequest createOrderRequest) {
	        // Ensure that the Order is correctly created using the service method
	        Order order = adminService.createOrder(createOrderRequest);
	        
	        // Return the created order inside ResponseEntity with HTTP 201 (Created) status
	        return new ResponseEntity<>(order, HttpStatus.CREATED);
	    }
	    //http://localhost:2000/admin/order/post
//	    {
//	    	  "customerId": 120,
//	    	  "restaurantId": 5,
//
//	    	  "orderItems": [
//	    	    {
//	    	      "menuItemId": 301,
//	    	      "quantity": 20,
//	    	      "costPerItem": 5.0,
//	    	      "totalCost": 10.0
//	    	    },
//	    	    {
//	    	      "menuItemId": 302,
//	    	      "quantity": 3,
//	    	      "costPerItem": 5.5,
//	    	      "totalCost": 16.5
//	    	    }
//	    	  ]
//	    	}


	    // Update order status
	    @PutMapping("/order/{id}/status")
	    public ResponseEntity<Order> updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
	        Order updatedOrder = adminService.updateOrderStatus(id, status);
	        if (updatedOrder != null) {
	            return new ResponseEntity<>(updatedOrder, HttpStatus.OK); // Return 200 with updated order
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if order not found
	        }
	    }
//http://localhost:2000/admin/order/16/status?status=completed
	    // Delete an order
	    @DeleteMapping("/order/{id}")
	    public ResponseEntity<Void> deleteOrder(@PathVariable Long id) {
	        try {
	            adminService.deleteOrder(id);
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Return 204 if successfully deleted
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Return 404 if order not found
	        }
	    }//http://localhost:2000/admin/order/4
	    
	    // customer-------------------
	    // Fetch all customers using Feign Client
	    @GetMapping("/customers/getall")
	    public ResponseEntity<List<Customer>> getAllCustomers() {
	        List<Customer> customers = adminService.getAllCustomers();
	        if (customers.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);  // Return HTTP 204 if no customers found
	        }
	        return new ResponseEntity<>(customers, HttpStatus.OK);  // Return HTTP 200 with customer list
	    }
	    //http://localhost:2000/admin/customers/getall

	    // Fetch customer by ID using Feign Client
	    @GetMapping("/customers/getbyid/{customerId}")
	    public ResponseEntity<Customer> getCustomerById(@PathVariable Long customerId) {
	        Customer customer = adminService.getCustomerById(customerId);
	        return customer != null
	                ? new ResponseEntity<>(customer, HttpStatus.OK)  // Return HTTP 200 if customer is found
	                : new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return HTTP 404 if customer is not found
	    }
	    //http://localhost:2000/admin/customers/getbyid/1

	    // Create customer using Feign Client
	    @PostMapping("/customers/post")
	    public ResponseEntity<Customer> createCustomer(@RequestBody Customer customer) {
	        Customer createdCustomer = adminService.createCustomer(customer);
	        return new ResponseEntity<>(createdCustomer, HttpStatus.CREATED);  // Return HTTP 201 after creating customer
	    }

	    // Update customer using Feign Client
	    @PutMapping("/customers/{customerId}")
	    public ResponseEntity<Customer> updateCustomer(@PathVariable Long customerId, @RequestBody Customer customer) {
	        Customer updatedCustomer = adminService.updateCustomer(customerId, customer);
	        return updatedCustomer != null
	                ? new ResponseEntity<>(updatedCustomer, HttpStatus.OK)  // Return HTTP 200 if update is successful
	                : new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return HTTP 404 if customer is not found
	    }

	    // Delete customer using Feign Client
	    @DeleteMapping("/customers/{customerId}")
	    public ResponseEntity<String> deleteCustomer(@PathVariable Long customerId) {
	        try {
	            adminService.deleteCustomer(customerId);
	            return new ResponseEntity<>("Customer deleted successfully", HttpStatus.NO_CONTENT);
	        } catch (Exception e) {
	            return new ResponseEntity<>("Customer not found", HttpStatus.NOT_FOUND);
	        }
	    }
	//restaurant---------------------------
	    @GetMapping("/restaurants/getall")
	    public List<Restaurant> getAllRestaurants() {
	        return adminService.getAllRestaurants();
	    }
	  //  http://localhost:2000/admin/restaurants/getall

	    // Get restaurant by ID
	    @GetMapping("/restaurants/getbyid/{id}")
	    public Restaurant getRestaurantById(@PathVariable Long id) {
	        return adminService.getRestaurantById(id);
	    }
	    //http://localhost:2000/admin/restaurants/getbyid/1

	    // Add a new restaurant
	    @PostMapping("/restaurants")
	    public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant) {
	        Restaurant addedRestaurant = adminService.addRestaurant(restaurant);
	        return ResponseEntity.ok(addedRestaurant);
	    }
	    //http://localhost:2000/admin/restaurants
//	 {
//	    "name": "Pasta Paradise",
//	    "cuisine": "Italian",
//	    "address": "123 Flavor Street, Food City",
//	    "menuItems": [
//	      {"id":1,
//	        "name": "Spaghetti Carbonara",
//	        "price": 15.99
//	      },
//	      {"id":2,
//	        "name": "Margherita Pizza",
//	        "price": 12.99
//	      }
//	    ]
//	  }
	    // Add a menu item to a restaurant
	    @PostMapping("/restaurants/{restaurantId}/menu")
	    public ResponseEntity<MenuItem> addMenuItem(@PathVariable Long restaurantId, @RequestBody MenuItem menuItem) {
	        MenuItem addedMenuItem = adminService.addMenuItem(restaurantId, menuItem);
	        return ResponseEntity.ok(addedMenuItem);
	    }
	    //http://localhost:2000/admin/restaurants/1/menu
//	    {
//	        "id": 2,
//	        "name": "Dosa",
//	        "price": 20.0
//	    }

	    // Get all menu items of a restaurant
	    @GetMapping("/restaurants/{restaurantId}/menu")
	    public ResponseEntity<List<MenuItem>> getMenuItems(@PathVariable Long restaurantId) {
	        List<MenuItem> menuItems = adminService.getMenuItems(restaurantId);
	        return ResponseEntity.ok(menuItems);
	    }
	    //http://localhost:2000/admin/restaurants/1/menu
	    
	    //-------------------paymentcontroller--------------------------------------------
	    // Create a payment for an order
	  
	    // Get all payments
	    @GetMapping("/payment/getall")
	    public ResponseEntity<List<Payment>> getAllPayments() {
	        List<Payment> payments = adminService.getAllPayments();
	        return ResponseEntity.ok(payments);
	    }
	    //http://localhost:2000/admin/payment/getall

	    // Get payment by ID
	    @GetMapping("/payment/getbyid/{id}")
	    public ResponseEntity<Payment> getPaymentById(@PathVariable Long id) {
	        Payment payment = adminService.getPaymentById(id);
	        if (payment == null) {
	            return ResponseEntity.notFound().build();  // Return 404 if payment is not found
	        }
	        return ResponseEntity.ok(payment);  // Return payment details with HTTP 200
	    }
	    //http://localhost:2000/admin/payment/getbyid/1
	    

	    // Process a payment
	    @PostMapping("/payment/process")
	    public ResponseEntity<Payment> processPayment(@RequestBody PaymentDTO paymentDTO) {
	        try {
	            Payment payment = adminService.processPayment(paymentDTO);
	            return new ResponseEntity<>(payment, HttpStatus.CREATED);  // Return 201 if payment is processed successfully
	        } catch (Exception e) {
	            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Return 400 if there is an error
	        }
	    }
	    //http://localhost:2000/admin/payment/process
//	    {
//	        "id": 14,
//	        "orderId": 1,
//	        "paymentMethod": "Credit Card",
//	        "paymentStatus": "Success",
//	        "paymentDate": "2024-11-22T14:30:00.000+00:00",
//	        "customerId": 12345,
//	        "totalCost": 1300.0
//	    }
	    //-----------------------notification controller-------------------------
	    // Send a generic notification
	    @PostMapping("/notification/send")
	    public ResponseEntity<Notification> sendNotification(@RequestBody NotificationDTO notificationDTO) {
	        Notification notification = adminService.sendNotification(notificationDTO);
	        return new ResponseEntity<>(notification, HttpStatus.CREATED);
	    }
	    //http://localhost:2000/admin/notification/send
//	    {
//	        "id": 22,
//	        "recipient": "+1234567890",
//	        "message": "Your pizza order has been dispatched!",
//	        "type": "SMS",
//	        "status": "Pending",
//	        "timestamp": "2024-11-22T22:42:12.9288052"
//	    }

	    // Send SMS Notification
	    @PostMapping("/notification/sms")
	    public ResponseEntity<Notification> sendSms(@RequestBody NotificationDTO notificationDTO) {
	        Notification notification = adminService.sendSms(notificationDTO);
	        return new ResponseEntity<>(notification, HttpStatus.CREATED);
	    }
	    //http://localhost:2000/admin/notification/sms
//	    {
//	    	  "recipient": "+1234567890",
//	    	  "message": "Your pizza order has been dispatched!",
//	    	  "status": "Sent",
//	    	  "restaurantName": "Pizza Hut"
//	    	}
	    //no need to pass the typeofmessage


	    // Send Email Notification
	    @PostMapping("/notification/email")
	    public ResponseEntity<Notification> sendEmail(@RequestBody NotificationDTO notificationDTO) {
	        Notification notification = adminService.sendEmail(notificationDTO);
	        return new ResponseEntity<>(notification, HttpStatus.CREATED);
	    }
	    //http://localhost:2000/admin/notification/email
//	    {
//	    	  "recipient": "+1234567890",
//	    	  "message": "Your pizza order has been dispatched!",
//	    	  "status": "Sent",
//	    	  "restaurantName": "Pizza Hut"
//	    	}


	    // Send WhatsApp Notification
	    @PostMapping("/notification/whatsapp")
	    public ResponseEntity<Notification> sendWhatsAppNotification(@RequestBody NotificationDTO notificationDTO) {
	        Notification notification = adminService.sendWhatsAppNotification(notificationDTO);
	        return new ResponseEntity<>(notification, HttpStatus.CREATED);
	    }
	    //http://localhost:2000/admin/notification/whatsapp
//	    {
//	    	  "recipient": "+1234567890",
//	    	  "message": "Your pizza order has been dispatched!",
//	    	  "status": "Sent",
//	    	  "restaurantName": "Pizza Hut"
//	    	}


	    // Send Push Notification
	    @PostMapping("/notification/push")
	    public ResponseEntity<Notification> sendPushNotification(@RequestBody NotificationDTO notificationDTO) {
	        Notification notification = adminService.sendPushNotification(notificationDTO);
	        return new ResponseEntity<>(notification, HttpStatus.CREATED);
	    }//same for post 
	    
	 // Endpoint to fetch all notifications
	    @GetMapping("/notification/all")
	    public ResponseEntity<List<Notification>> getAllNotifications() {
	        List<Notification> notifications = adminService.getAllNotifications();
	        if (notifications.isEmpty()) {
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content if no notifications found
	        }
	        return new ResponseEntity<>(notifications, HttpStatus.OK); // 200 OK with list of notifications
	    }
	    //http://localhost:2000/admin/notification/all

	    // Endpoint to fetch notification by ID
	    @GetMapping("/notification/{id}")
	    public ResponseEntity<Notification> getNotificationById(@PathVariable Long id) {
	        Notification notification = adminService.getNotificationById(id);
	        if (notification == null) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // 404 Not Found if notification not found
	        }
	        return new ResponseEntity<>(notification, HttpStatus.OK); // 200 OK with notification details
	    }
	    //http://localhost:2000/admin/notification/1
	    //results
//	    {
//	        "id": 1,
//	        "recipient": "a@gmail.com",
//	        "message": "Visit again",
//	        "type": "SMS",
//	        "status": "Success",
//	        "timestamp": "2024-11-11T05:30:00"
//	    }
	
	    
}