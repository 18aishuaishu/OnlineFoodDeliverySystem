package com.Restaurant_Service.Service;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.Restaurant_Service.DTO.NotificationDTO;
import com.Restaurant_Service.Entity.Customer;
import com.Restaurant_Service.Entity.Delivery;
import com.Restaurant_Service.Entity.MenuItem;
import com.Restaurant_Service.Entity.Order;
import com.Restaurant_Service.Entity.Payment;
//import com.Restaurant_Service.Entity.MenuItem;
import com.Restaurant_Service.Entity.Restaurant;
import com.Restaurant_Service.FeignClient.CustomerFeignClient;
import com.Restaurant_Service.FeignClient.DeliveryFeignClient;
import com.Restaurant_Service.FeignClient.NotificationClient;
import com.Restaurant_Service.FeignClient.OrdersFeignClient;
import com.Restaurant_Service.FeignClient.PaymentFeignClient;
import com.Restaurant_Service.Repository.MenuItemRepository;
import com.Restaurant_Service.Repository.RestaurantRepository;
//import com.ctc.wstx.shaded.msv_core.datatype.xsd.Comparator;

import jakarta.transaction.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
@Service
public class RestaurantService {

    @Autowired
    private RestaurantRepository restaurantRepository;

    @Autowired
    private MenuItemRepository menuItemRepository;
    
    @Autowired
    private NotificationClient notificationClient;
    @Autowired
    private PaymentFeignClient paymentFeignClient;
    @Autowired
    private OrdersFeignClient ordersFeignClient;
    @Autowired
    private CustomerFeignClient customerFeignClient;
    @Autowired
    private DeliveryFeignClient deliveryFeignClient;

    
    // Add a new Restaurant with Menu Items
    @Transactional
    public Restaurant addRestaurant(Restaurant restaurant) {
        // Associate menu items with the restaurant
        if (restaurant.getMenuItems() != null) {
            for (MenuItem menuItem : restaurant.getMenuItems()) {
                menuItem.setRestaurant(restaurant); // Set the restaurant to the menu item
            }
        }

        // Save the restaurant (menu items will be saved automatically because of cascade)
        return restaurantRepository.save(restaurant);
    }

    
    // Fetch all restaurants
    public List<Restaurant> getAllRestaurants() {
        return restaurantRepository.findAll();
    }
    // Delete restaurant by ID
    public void deleteRestaurant(Long id) {
        // First, check if the restaurant exists
        if (restaurantRepository.existsById(id)) {
            // Delete the restaurant (this will also delete related menu items due to cascading)
            restaurantRepository.deleteById(id);
        } else {
            throw new RuntimeException("Restaurant not found with ID: " + id);
        }
    }
    // Fetch a restaurant by ID
    public Restaurant getRestaurantById(Long id) {
        return restaurantRepository.findById(id).orElse(null);
    }

    // Add menu item to a specific restaurant
    public MenuItem addMenuItemToRestaurant(Long restaurantId, MenuItem menuItem) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        if (restaurant != null) {
            menuItem.setRestaurant(restaurant);
            return menuItemRepository.save(menuItem);
        }
        throw new RuntimeException("Restaurant not found");
    }

    // Get all menu items for a restaurant
    public List<MenuItem> getMenuItemsByRestaurant(Long restaurantId) {
        Restaurant restaurant = restaurantRepository.findById(restaurantId).orElse(null);
        if (restaurant != null) {
            return restaurant.getMenuItems();
        }
        throw new RuntimeException("Restaurant not found");
    }

  //  ------------------------------------

    // Method to notify customer about order updates, including the restaurant name
    public void sendOrderNotification(String recipient, String message, String type, String restaurantName) {
        // Prepare NotificationDTO with the restaurant name
        NotificationDTO notificationDTO = new NotificationDTO();
        notificationDTO.setRecipient(recipient);
        notificationDTO.setMessage(message);
        notificationDTO.setType(type);
        notificationDTO.setStatus("Sent");  // Set initial status as 'Sent'
        notificationDTO.setRestaurantName(restaurantName);  // Set the restaurant name

        // Use FeignClient to send notification
        notificationClient.sendNotification(notificationDTO);
    }

    // Method to update notification status
    public void updateNotificationStatus(Long notificationId, String status) {
        // Update the notification status by calling the FeignClient
        notificationClient.updateNotificationStatus(notificationId, status);
    }
//payment
    // Get all payments from the Payment Service
    public List<Payment> getAllPayments() {
        return paymentFeignClient.getAllPayments();
    }

    public Payment getById(Long id) {
        return paymentFeignClient.getById(id);
    }
    //orders
    // Endpoint to get all orders
    public List<Order> getAllOrders() {
        return ordersFeignClient.getAllOrders(); // Fetches all orders from Order Service
    }

    // Endpoint to update order status
    public Order updateOrderStatus(Long id, String status) {
        return ordersFeignClient.updateOrderStatus(id, status); // Updates the status of the order
    }
    // Get orders by a specific customer and restaurant
    public List<Order> getOrdersByCustomerAndRestaurant(Long customerId, Long restaurantId) {
        ResponseEntity<List<Order>> response = ordersFeignClient.getOrdersByCustomerAndRestaurant(customerId, restaurantId);
        
        // If orders are found, return them; otherwise, return an empty list
        if (response.getBody() != null && !response.getBody().isEmpty()) {
            return response.getBody();
        }
        
        return new ArrayList<Order>();
    }
    //customer service logic ..restaurant view the customer details
 // Method to get all customers using Feign Client
    public List<Customer> getAllCustomers() {
        // Call the Feign Client to fetch the list of customers
        return customerFeignClient.getAllCustomers();
    }
    //restaurant view the delivery details and update the delivery
 // Get all deliveries from the Delivery Service
    public List<Delivery> getAllDeliveries() {
        return deliveryFeignClient.getAllDeliveries();
    }

    // Update delivery status using Feign Client
    public Delivery updateDeliveryStatus(Long id, String status) {
        return deliveryFeignClient.updateDeliveryStatus(id, status);
    }
    //-------------
    
 // Save or update the restaurant
    public Restaurant saveRestaurant(Restaurant restaurant) {
        return restaurantRepository.save(restaurant);
    }
//newchange----------------------
    @Transactional
    public Restaurant updateRestaurantt(Long id, Restaurant restaurant) {
        Optional<Restaurant> existingRestaurant = restaurantRepository.findById(id);
        if (existingRestaurant.isPresent()) {
            Restaurant updatedRestaurant = existingRestaurant.get();
            updatedRestaurant.setName(restaurant.getName());
            updatedRestaurant.setCuisine(restaurant.getCuisine());
            updatedRestaurant.setAddress(restaurant.getAddress());
            updatedRestaurant.setImageUrl(restaurant.getImageUrl());

            // Update menu items if they exist
            for (int i = 0; i < restaurant.getMenuItems().size(); i++) {
                MenuItem menuItem = restaurant.getMenuItems().get(i);
                if (menuItem.getId() != null) {
                    // Update existing menu item
                    updatedRestaurant.getMenuItems().get(i).setName(menuItem.getName());
                    updatedRestaurant.getMenuItems().get(i).setPrice(menuItem.getPrice());
                    updatedRestaurant.getMenuItems().get(i).setImageUrl(menuItem.getImageUrl());
                } else {
                    // Add new menu item
                    menuItem.setRestaurant(updatedRestaurant);
                    updatedRestaurant.getMenuItems().add(menuItem);
                }
            }
            return restaurantRepository.save(updatedRestaurant);
        }
        return null; // Handle case where the restaurant does not exist
    }
    

}

