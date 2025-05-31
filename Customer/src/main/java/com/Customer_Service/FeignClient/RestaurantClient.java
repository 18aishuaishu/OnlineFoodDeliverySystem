package com.Customer_Service.FeignClient;


import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Customer_Service.Entity.MenuItem;
import com.Customer_Service.Entity.Restaurant;

@FeignClient(name = "restaurant", url = "http://localhost:2005/restaurants")  // Adjust the URL to your restaurant service's URL
public interface RestaurantClient {

	// Get all restaurants
    @GetMapping("/getall")
    public List<Restaurant> getAllRestaurants() ;

    // Get all menu items of a restaurant
    @GetMapping("/{restaurantId}/menu")
    public List<MenuItem> getMenuItems(@PathVariable Long restaurantId) ;

	  
	}

