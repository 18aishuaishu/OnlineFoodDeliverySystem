package com.Restaurant_Service.Controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
//import org.springframework.security.access.prepost.PreAuthorize;
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

import com.Restaurant_Service.DTO.NotificationDTO;
import com.Restaurant_Service.Entity.Customer;
import com.Restaurant_Service.Entity.Delivery;
import com.Restaurant_Service.Entity.MenuItem;
import com.Restaurant_Service.Entity.Order;
import com.Restaurant_Service.Entity.Payment;
import com.Restaurant_Service.Entity.Restaurant;
import com.Restaurant_Service.Repository.MenuItemRepository;
import com.Restaurant_Service.Repository.RestaurantRepository;
import com.Restaurant_Service.Service.RestaurantService;


@RestController
@RequestMapping("/restaurants")
@CrossOrigin(origins = "http://localhost:4200")  // Allow CORS from Angular app

public class RestaurantController {
    @Autowired
    private RestaurantService restaurantService;
    @Autowired
    private RestaurantRepository rr;
    @Autowired
    private MenuItemRepository mr;    
    
    
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteRestaurant(@PathVariable Long id) {
        try {
            restaurantService.deleteRestaurant(id);
            return ResponseEntity.ok("Restaurant deleted successfully.");
        } catch (Exception e) {
            return ResponseEntity.status(404).body("Restaurant not found.");
        }
    }
    @PostMapping("/{restaurantId}/menus")
    public ResponseEntity<List<MenuItem>> addMenuItemsToRestaurant(
        @PathVariable Long restaurantId,
        @RequestBody List<MenuItem> menuItems) {

        // Find the restaurant by ID
        Optional<Restaurant> restaurantOpt = rr.findById(restaurantId);
        if (!restaurantOpt.isPresent()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build(); // 404 if the restaurant doesn't exist
        }

        Restaurant restaurant = restaurantOpt.get();

        // Ensure menu items have valid names and prices
        for (MenuItem menuItem : menuItems) {
            if (menuItem.getName() == null || menuItem.getName().isEmpty() || menuItem.getPrice() <= 0) {
                return ResponseEntity.badRequest().body(Collections.singletonList(menuItem));
            }
            menuItem.setRestaurant(restaurant);  // Associate menu item with the restaurant
            mr.save(menuItem);   // Save menu item
        }

        return ResponseEntity.ok(menuItems);  // Return the saved menu items
    }
    //http://localhost:2005/restaurants/4/menus
    //this one is correct^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    
//    [
//     {
//       "name": "Pizza aa",
//       "price": 12.99,
//       "restaurant": {
//         "id": 5
//       }
//     },
//     {
//       "name": "Pizza bb",
//       "price": 14.99,
//       "restaurant": {
//         "id": 5
//       }
//     }
//]
    

    // Get all restaurants
    @GetMapping("/getall")   
//    @PreAuthorize("hasRole('ADMIN')") // Only admin can view all restaurants
    public List<Restaurant> getAllRestaurants() {
        return restaurantService.getAllRestaurants();
    }
    //http://localhost:2005/restaurants/getall
    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^

 
 // Fetch MenuItem with restaurant details
    @GetMapping("/menuitem/{id}")
    public ResponseEntity<MenuItem> getMenuItem(@PathVariable Long id) {
        Optional<MenuItem> menuItem = mr.findById(id);
        if (menuItem.isPresent()) {
            return ResponseEntity.ok(menuItem.get());
        }
        return ResponseEntity.notFound().build();
    } // Fetch all menu items for a restaurant
    
    
    //http://localhost:2005/restaurants/menuitem/1
    //^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    @GetMapping("/restaurant/{restaurantId}")
    public ResponseEntity<List<MenuItem>> getMenuItemsByRestaurant(@PathVariable Long restaurantId) {
        List<MenuItem> menuItems = mr.findByRestaurantId(restaurantId);
        return ResponseEntity.ok(menuItems);
    }
    //---------------------------------------fetch menu items by restaurant id^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^
    @PostMapping("/add")
    public ResponseEntity<MenuItem> createMenuItem(@RequestBody MenuItem menuItem) {
        Long restaurantId = menuItem.getRestaurant().getId();
        Optional<Restaurant> restaurant = rr.findById(restaurantId);
        if (restaurant.isPresent()) {
            menuItem.setRestaurant(restaurant.get());
            MenuItem savedMenuItem = mr.save(menuItem);
            return ResponseEntity.ok(savedMenuItem);
        }
        return ResponseEntity.badRequest().build();
    }
    // Get a restaurant by ID
    @GetMapping("/getbyid/{id}")
//    @PreAuthorize("hasRole('ADMIN') or hasRole('USER')") // Both admins and users can view restaurant details

    public ResponseEntity<Restaurant> getRestaurantById(@PathVariable Long id) {
        Restaurant restaurant = restaurantService.getRestaurantById(id);
        if (restaurant != null) {
            return ResponseEntity.ok(restaurant);
        }
        return ResponseEntity.notFound().build();
    }
//http://localhost:2005/restaurants/getbyid/1
    ///getby id for see restaurant details using restaurant id along with menu items
    
//    {
//        "id": 1,
//        "name": "Biriyani House",
//        "cuisine": "Indian",
//        "address": "E Nagar",
//        "menuItems": [
//            {
//                "id": 27,
//                "name": "Pizza Margherita",
//                "price": 12.99
//            },
//            {
//                "id": 28,
//                "name": "Pizza Margherita",
//                "price": 12.99
//            },
//            {
//                "id": 29,
//                "name": "Pizza aa",
//                "price": 12.99
//            },
//            {
//                "id": 33,
//                "name": null,
//                "price": 0.0
//            }
//        ]
//    }
    
    
    // Add a new restaurant
    @PostMapping("/post")
//    @PreAuthorize("hasRole('ADMIN')")

    public ResponseEntity<Restaurant> addRestaurant(@RequestBody Restaurant restaurant) {
        // Add the restaurant with its menu items
        Restaurant savedRestaurant = restaurantService.addRestaurant(restaurant);
        return ResponseEntity.ok(savedRestaurant);
    }
    
    //http://localhost:2005/restaurants/post
    
//    {
//     	  "name": "Biriyani home1",
//     	  "cuisine": "Indian",
//     	  "address": "E Nagar",
//     	  "menuItems": [
//     	    {
//     	      "name": "BiriyaniIndian",
//     	      "price": 30.0
//     	    },
//     	    {
//     	      "name": "Biriyani chinese",
//     	      "price": 40.0
//     	    },
//     	    {
//     	      "name": "Porotta",
//     	      "price": 25.0
//     	    }
//     	  ]
//     	}
    
    //working add restaurant details with menu items^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^



    
    // Add a menu item to a restaurant
    @PostMapping("/{restaurantId}/menu")
    public ResponseEntity<MenuItem> addMenuItem(@PathVariable Long restaurantId, @RequestBody MenuItem menuItem) {
        MenuItem savedMenuItem = restaurantService.addMenuItemToRestaurant(restaurantId, menuItem);
        return ResponseEntity.ok(savedMenuItem);
    }


    // Get all menu items of a restaurant
    @GetMapping("/{restaurantId}/menu")
    public ResponseEntity<List<MenuItem>> getMenuItems(@PathVariable Long restaurantId) {
        List<MenuItem> menuItems = restaurantService.getMenuItemsByRestaurant(restaurantId);
        return ResponseEntity.ok(menuItems);
    }

    //notofication
    @PostMapping("/notification/sendnotification")
    public ResponseEntity<String> notifyOrderUpdate(@RequestBody NotificationDTO notificationDTO) {
        try {
            // Call service to send the order notification
            restaurantService.sendOrderNotification(notificationDTO.getRecipient(), 
                                                     notificationDTO.getMessage(), 
                                                     notificationDTO.getType(), notificationDTO.getRestaurantName());
            return new ResponseEntity<>("Notification sent successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to send notification", HttpStatus.BAD_REQUEST);
        }
    }
    //http://localhost:2005/restaurants/notification/sendnotification
    //jsonbody
//    {
//    	  "recipient": "customer123@domain.com",
//    	  "message": "Your order from Pizza Palace is on its way!",
//    	  "type": "SMS",
//    	  "restaurantName": "PizzaPalace"
//    	}

    @PostMapping("/sendnotification")
    public ResponseEntity<String> sendOrderNotification(
            @RequestParam String recipient, 
            @RequestParam String message, 
            @RequestParam String type,
            @RequestParam String restaurantName) {

        try {
            // Call the service method to send notification
            restaurantService.sendOrderNotification(recipient, message, type, restaurantName);
            return new ResponseEntity<>("Notification sent successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to send notification", HttpStatus.BAD_REQUEST);
        }
    }//above same

    // Update notification status
    @PutMapping("/updateNotificationStatus/{notificationId}")
    public ResponseEntity<String> updateNotificationStatus(
            @PathVariable Long notificationId,
            @RequestParam String status) {

        try {
            // Call the service method to update the notification status
            restaurantService.updateNotificationStatus(notificationId, status);
            return new ResponseEntity<>("Notification status updated successfully", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Failed to update notification status", HttpStatus.BAD_REQUEST);
        }
    }

    // Get all payments
    @GetMapping("/payments/all")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = restaurantService.getAllPayments();
        
        if (payments.isEmpty()) {
            return ResponseEntity.noContent().build(); // 204 No Content if no payments found
        }
        
        return ResponseEntity.ok(payments); // 200 OK with list of payments
    }
    //http://localhost:2005/restaurants/payments/all
    

    // Get payment by paymentId
    @GetMapping("/paymentbyid/{id}")
    public ResponseEntity<Payment> getById(@PathVariable Long id) {
        Payment payment = restaurantService.getById(id);
        
        if (payment == null) {
            return ResponseEntity.notFound().build(); // 404 Not Found if payment not found
        }
        
        return ResponseEntity.ok(payment); // 200 OK with payment details
    }
    //http://localhost:2005/restaurants/paymentbyid/1
    
    // Endpoint to view all orders
    @GetMapping("/orders/getall")
    public List<Order> getAllOrders() {
        // Fetch all orders from the Order Service
        return restaurantService.getAllOrders();
    }//http://localhost:2005/restaurants/orders/getall
        

    // Endpoint to update the status of an order
    @PutMapping("/orders/{id}/status")
    public Order updateOrderStatus(@PathVariable Long id, @RequestParam String status) {
        // Update the order status using the Order Service
        return restaurantService.updateOrderStatus(id, status);
    }
    
//http://localhost:2005/api/restaurants/orders/3/status?status=Cancelled
    // Get all orders by a particular customer at a specific restaurant
    @GetMapping("/{restaurantId}/orders/customer/{customerId}")
    public ResponseEntity<List<Order>> getOrdersByCustomerAndRestaurant(
            @PathVariable("restaurantId") Long restaurantId,
            @PathVariable("customerId") Long customerId) {
        
        // Call the service method to fetch orders
        List<Order> orders = restaurantService.getOrdersByCustomerAndRestaurant(customerId, restaurantId);

        // If no orders found, return 404 (Not Found)
        if (orders.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        // Return the orders with a 200 OK response
        return ResponseEntity.ok(orders);
    }
    //http://localhost:2005/restaurants/5/orders/customer/100
    
    // restaurant view the customer details
    // Endpoint to get all customers
    @GetMapping("/customers/getall")
    public ResponseEntity<List<Customer>> getAllCustomers() {
        List<Customer> customers = restaurantService.getAllCustomers();
        if (customers.isEmpty()) {
            return ResponseEntity.noContent().build();  // HTTP 204 if no customers found
        }
        return ResponseEntity.ok(customers);  // HTTP 200 with customers list
    }
//http://localhost:2005/restaurants/customers/getall
    
    // Endpoint to get all deliveries
    @GetMapping("/delivery/getall")
    public ResponseEntity<List<Delivery>> getAllDeliveries() {
        List<Delivery> deliveries = restaurantService.getAllDeliveries();
        if (deliveries.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // HTTP 204 if no deliveries
        }
        return new ResponseEntity<>(deliveries, HttpStatus.OK); // HTTP 200 with deliveries list
    }
//http://localhost:2005/restaurants/delivery/getall
    
    // Endpoint to update the delivery status
    @PutMapping("/delivery/{id}/status")
    public ResponseEntity<Delivery> updateDeliveryStatus(@PathVariable Long id, @RequestParam String status) {
        try {
            Delivery updatedDelivery = restaurantService.updateDeliveryStatus(id, status);
            return new ResponseEntity<>(updatedDelivery, HttpStatus.OK); // HTTP 200 with updated delivery
        } catch (RuntimeException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // HTTP 404 if delivery not found
        }
    }
    // http://localhost:2005/restaurants/delivery/1/status?status=completed
    
    
    //-----------------new for update
    @PutMapping("/update/{id}")
    public ResponseEntity<Restaurant> updateRestaurant(@PathVariable Long id, @RequestBody Restaurant updatedRestaurant) {
        try {
            // Attempt to find the restaurant by ID
            Restaurant existingRestaurant = restaurantService.getRestaurantById(id);

            if (existingRestaurant == null) {
                return ResponseEntity.notFound().build(); // Return 404 if restaurant not found
            }

            // Set the updated values to the existing restaurant
            existingRestaurant.setName(updatedRestaurant.getName());
            existingRestaurant.setCuisine(updatedRestaurant.getCuisine());
            existingRestaurant.setAddress(updatedRestaurant.getAddress());

            // Update the menu items
            existingRestaurant.setMenuItems(updatedRestaurant.getMenuItems());

            // Save the updated restaurant
            restaurantService.saveRestaurant(existingRestaurant);

            return ResponseEntity.ok(existingRestaurant); // Return the updated restaurant
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Return 500 if there's an error
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<Restaurant> updateRestaurantt(@PathVariable Long id, @RequestBody Restaurant restaurant) {
        restaurant.setId(id); // Ensure the restaurant ID is set
        Restaurant updatedRestaurant = restaurantService.updateRestaurantt(id, restaurant);
        return ResponseEntity.ok(updatedRestaurant);

}
}
