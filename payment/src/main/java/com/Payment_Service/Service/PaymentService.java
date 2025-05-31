package com.Payment_Service.Service;

import com.Payment_Service.DTO.PaymentDTO;
import com.Payment_Service.Entity.Order;
import com.Payment_Service.Entity.Payment;
import com.Payment_Service.FeignClient.OrderServiceClient;
import com.Payment_Service.Repository.PaymentRepository;

import jakarta.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private OrderServiceClient orderServiceClient;


    public Payment createPayment(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        payment.setOrderId(paymentDTO.getOrderId());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setTotalCost(paymentDTO.getTotalCost());
        payment.setPaymentDate(paymentDTO.getPaymentDate());
        payment.setCustomerId(paymentDTO.getCustomerId());
        payment.setPaymentStatus("Pending");

        // Save the Payment entity to the database
        return paymentRepository.save(payment);
    }
    
    public Payment createPayment(Payment payment) {
        // Payment logic (could involve third-party payment gateways, validation, etc.)
        return paymentRepository.save(payment);
    }
    // Get all payments
    public List<Payment> getAllPayments() {
        return paymentRepository.findAll();
    }
    

    // Get payment by paymentId
    public Payment getById(Long id) {
        return paymentRepository.findById(id).orElseThrow(() -> new RuntimeException("Payment not found"));
    }

    // Update payment status (e.g., Pending -> Success or Failed)
    public Payment updatePaymentStatus(Long paymentId, String status) {
        Payment payment = paymentRepository.findById(paymentId).orElseThrow(() -> new RuntimeException("Payment not found"));
        payment.setPaymentStatus(status);
        return paymentRepository.save(payment);
    }
    
  
//    // Method to create a payment (could be used in future if needed)
//    public Payment createPayment(Long orderId, String paymentMethod, double totalCost) {
//        Payment payment = new Payment();
//        payment.setOrderId(orderId);
//        payment.setPaymentMethod(paymentMethod);
//        payment.setTotalCost(totalCost);
//        payment.setPaymentStatus("Pending");
//        payment.setPaymentDate(new Date());
//        return paymentRepository.save(payment);
//    }
    // Method to process payment and save it to the database

    public Payment processPayment(PaymentDTO paymentDTO) {
        Payment payment = new Payment();
        
        // Map fields from DTO
        payment.setOrderId(paymentDTO.getOrderId());
        payment.setCustomerId(paymentDTO.getCustomerId());
        payment.setPaymentMethod(paymentDTO.getPaymentMethod());
        payment.setTotalCost(paymentDTO.getTotalCost());
        
        // Set additional fields
        payment.setPaymentDate(new Date());  // Set current date/time
        payment.setPaymentStatus("Success");   // Set default status (or update based on your business logic)

        // Save and return the Payment entity
        return paymentRepository.save(payment);
    }

   
    // Fetch all payments made by a customer
    public List<Payment> getPaymentsByCustomer(Long customerId) {
        return paymentRepository.findByCustomerId(customerId);
    }  
    
    //new change----------------------------
    // Update Payment
    @Transactional
    public Payment updatePayment(Long paymentId, Payment paymentDetails) {
        Optional<Payment> existingPayment = paymentRepository.findById(paymentId);
        if (existingPayment.isPresent()) {
            Payment payment = existingPayment.get();
            payment.setCustomerId(paymentDetails.getCustomerId());
            payment.setOrderId(paymentDetails.getOrderId());
            payment.setPaymentMethod(paymentDetails.getPaymentMethod());
            payment.setTotalCost(paymentDetails.getTotalCost());
            payment.setPaymentStatus(paymentDetails.getPaymentStatus());
            payment.setPaymentDate(paymentDetails.getPaymentDate());
            return paymentRepository.save(payment);  // Save the updated payment
        }
        return null; // Payment not found
    }

    // Delete Payment
    @Transactional
    public boolean deletePayment(Long paymentId) {
        Optional<Payment> payment = paymentRepository.findById(paymentId);
        if (payment.isPresent()) {
            paymentRepository.delete(payment.get());  // Delete the payment
            return true;
        }
        return false; // Payment not found
    }
    
   
 
       
    public Payment getPaymentByOrderId(Long orderId) {
        // Use the repository to find payment by orderId
        return paymentRepository.findByOrderId(orderId);
    }

        // Other methods (createPayment, etc.) can go here...
    // Fetch payment details by Order ID
    public Payment getPaymentDetails(Long orderId) {
        System.out.println("🔍 Fetching payment details for Order ID: " + orderId);
        return paymentRepository.findByOrderId(orderId);
    }    
}


