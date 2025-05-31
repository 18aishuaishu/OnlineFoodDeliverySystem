package com.Delivery_Service.Service;

import org.springframework.stereotype.Service;

import com.Delivery_Service.Entity.Customer;
import com.Delivery_Service.Entity.Delivery;
import com.Delivery_Service.FeignClient.CustomerServiceFeignClient;
import com.Delivery_Service.Repository.DeliveryRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class DeliveryService {
	
	    @Autowired
	    private DeliveryRepository deliveryRepository;
	    @Autowired
	    private CustomerServiceFeignClient customerfc;

	    // Create a new delivery record
	    public Delivery createDelivery(Delivery delivery) {
	        return deliveryRepository.save(delivery);
	    }

	    // Get a delivery by its ID
	    public Optional<Delivery> getDeliveryById(Long id) {
	        return deliveryRepository.findById(id);
	    }

	    // Update an existing delivery status
	    public Delivery updateDeliveryStatus(Long id, String status) {
	        Delivery delivery = deliveryRepository.findById(id)
	                .orElseThrow(() -> new RuntimeException("Delivery not found"));
	        delivery.setStatus(status);
	        return deliveryRepository.save(delivery);
	    }

	    // Get all deliveries
	    public List<Delivery> getAllDeliveries() {
	        return deliveryRepository.findAll();
	    }

	    // Delete a delivery by ID
	    public void deleteDelivery(Long id) {
	        deliveryRepository.deleteById(id);
	    }
	    // Fetch all deliveries for a specific customer
	    
	    // Fetch customer details from Customer service
	    // Example method for fetching customer details
	    public Customer getCustomerDetails(Long customerId) {
	        return customerfc.getCustomerById(customerId);  // Use the Feign client to fetch customer details
	    }
	    
	  
	    

	        public List<Delivery> getDeliveriesByCustomer(Long customerId) {
	            return deliveryRepository.findByCustomerId(customerId);
	        }
	    }

	
