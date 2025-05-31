package com.Admin_Service.FeignClient;

import com.Admin_Service.Entity.Customer;
import com.Admin_Service.Entity.Delivery;
import com.Admin_Service.Entity.MenuItem;
import com.Admin_Service.Entity.Order;
import com.Admin_Service.Entity.Restaurant;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@FeignClient(name = "Restaurant", url = "http://localhost:2005/restaurants") // URL or Service Name for Restaurant
																					// Service
public interface RestaurantServiceFeignClient {

	@GetMapping("/getall")
	List<Restaurant> getAllRestaurants();

	@GetMapping("/{restaurantId}/menu")
	List<MenuItem> getMenuItems(@PathVariable Long restaurantId);

	// Get restaurant by ID
	@GetMapping("/getbyid/{id}")
	Restaurant getRestaurantById(@PathVariable Long id);

	// Get all customers
	@GetMapping("/customers/getall")
	List<Customer> getAllCustomers();

	// Get all deliveries
	@GetMapping("/delivery/getall")
	List<Delivery> getAllDeliveries();

	// Get all orders
	@GetMapping("/orders/getall")
	List<Order> getAllOrders();

	// Send notification
	@PostMapping("/sendnotification")
	ResponseEntity<String> sendOrderNotification(@RequestParam String recipient, @RequestParam String message,
			@RequestParam String type, @RequestParam String restaurantName);

	// Update order status
	@PutMapping("/orders/{id}/status")
	Order updateOrderStatus(@PathVariable Long id, @RequestParam String status);

	// Update delivery status
	@PutMapping("/{id}/status")
	ResponseEntity<Delivery> updateDeliveryStatus(@PathVariable Long id, @RequestParam String status);

	@PostMapping("/post")
	Restaurant addRestaurant(@RequestBody Restaurant restaurant);

	// Add a menu item to a restaurant
	@PostMapping("/{restaurantId}/menu")
	MenuItem addMenuItem(@PathVariable("restaurantId") Long restaurantId, @RequestBody MenuItem menuItem);

}
