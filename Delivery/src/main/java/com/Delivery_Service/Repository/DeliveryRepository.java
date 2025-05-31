package com.Delivery_Service.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Delivery_Service.Entity.Delivery;

import java.util.List;
import java.util.Optional;
@Repository
public interface DeliveryRepository extends JpaRepository<Delivery, Long> {

	Optional<Delivery> findDeliveryById(Long id);

    List<Delivery> findByCustomerId(Long customerId);

	//Object findByOrderId(Long orderId);
   //  Delivery updateDeliveryStatus(Long orderId, String status, String trackingNumber);
    //Optional<Delivery> findByOrderId(Long orderId);

    // You can add more custom queries if needed
}

