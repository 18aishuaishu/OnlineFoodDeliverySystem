package com.Delivery_Service.Controller;

import com.Delivery_Service.Entity.Customer;
import com.Delivery_Service.Entity.Delivery;
import com.Delivery_Service.Service.DeliveryService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/deliveries")
@CrossOrigin(origins = "http://localhost:4200")

public class DeliveryController {
	
	    @Autowired
	    private DeliveryService deliveryService;
	    
	    // Fetc	h deliveries by customer ID
	    // Get customer details for a delivery
	    @GetMapping("/customer/{id}")
	    public ResponseEntity<Customer> getCustomerDetails(@PathVariable Long id) {
	        Optional<Delivery> delivery = deliveryService.getDeliveryById(id);
	        if (delivery.isPresent()) {
	            Long customerId = delivery.get().getCustomerId();  // Fetch customer ID from the delivery
	            Customer customer = deliveryService.getCustomerDetails(customerId);  // Fetch customer details using Feign Client
	            return ResponseEntity.ok(customer);
	        } else {
	            return ResponseEntity.notFound().build();
	        }
	    }
	    // Create a new delivery
	    @PostMapping("/post")
	    public ResponseEntity<Delivery> createDelivery(@RequestBody Delivery delivery) {
	        Delivery createdDelivery = deliveryService.createDelivery(delivery);
	        return new ResponseEntity<>(createdDelivery, HttpStatus.CREATED);
	    }
	    //http://localhost:2002/deliveries/post
//	    {
//	        "orderId": 3,
//	        "status": "shipped",
//	        "deliveryDate": "2024-11-11T05:30:00",
//	        "trackingNumber": "t2"
//	    }

	    // Get a delivery by ID
	    @GetMapping("/getbyid/{id}")
	    public ResponseEntity<Delivery> getDeliveryById(@PathVariable Long id) {
	        Optional<Delivery> delivery = deliveryService.getDeliveryById(id);
	        if (delivery.isPresent()) {
	            return new ResponseEntity<>(delivery.get(), HttpStatus.OK);
	        } else {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	    //http://localhost:2002/deliveries/getbyid/1

	    // Get all deliveries
	    @GetMapping("/getall")
	    public List<Delivery> getAllDeliveries() {
	        return deliveryService.getAllDeliveries();
	    }
	    //http://localhost:2002/deliveries/getall
	    
	    
	    // Update the status of a delivery
	    @PutMapping("/{id}/status")
	    public ResponseEntity<Delivery> updateDeliveryStatus(@PathVariable Long id, @RequestParam String status) {
	        try {
	            Delivery updatedDelivery = deliveryService.updateDeliveryStatus(id, status);
	            return new ResponseEntity<>(updatedDelivery, HttpStatus.OK);
	        } catch (RuntimeException e) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }
	    //http://localhost:2002/deliveries/4/status?status=Delivered

	    // Delete a delivery
	    @DeleteMapping("/{id}")
	    public ResponseEntity<Void> deleteDelivery(@PathVariable Long id) {
	        try {
	            deliveryService.deleteDelivery(id);
	            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
	        } catch (RuntimeException e) {
	            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
	        }
	    }//http://localhost:2002/deliveries/4
	   
	        @GetMapping("/customer/{customerId}")
	        public ResponseEntity<List<Delivery>> getDeliveriesByCustomer(@PathVariable Long customerId) {
	            List<Delivery> deliveries = deliveryService.getDeliveriesByCustomer(customerId);
	            if (deliveries.isEmpty()) {
	                return ResponseEntity.status(HttpStatus.NOT_FOUND).body(Collections.emptyList()); // Return empty list instead of null
	            }
	            return ResponseEntity.ok(deliveries);
	        }
	    }


