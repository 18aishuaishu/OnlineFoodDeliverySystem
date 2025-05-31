package com.Payment_Service.Repository;

import com.Payment_Service.Entity.Payment;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface PaymentRepository extends JpaRepository<Payment, Long> {

    // You can define custom queries if needed (e.g., find payments by status)
    Payment findByOrderId(Long orderId);

	List<Payment> findByCustomerId(Long customerId);
    
}
