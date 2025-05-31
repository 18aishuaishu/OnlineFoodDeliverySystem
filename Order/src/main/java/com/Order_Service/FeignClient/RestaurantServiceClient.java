package com.Order_Service.FeignClient;


import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import com.Order_Service.Entity.MenuItem;

import java.util.List;

@FeignClient(name = "restaurant", url = "http://localhost:2005/restaurants")  // Assuming the restaurant service is running at this URL
public interface RestaurantServiceClient {

	  @GetMapping("/{restaurantId}/menu")
	    List<MenuItem> getMenuItems(@PathVariable("restaurantId") Long restaurantId);
}