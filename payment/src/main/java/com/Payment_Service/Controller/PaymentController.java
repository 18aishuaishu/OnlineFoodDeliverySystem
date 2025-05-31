package com.Payment_Service.Controller;

import com.Payment_Service.DTO.PaymentDTO;

import com.Payment_Service.Entity.Payment;
import com.Payment_Service.Service.PaymentService;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
@RestController
@RequestMapping("/payments")
@CrossOrigin(origins = "http://localhost:4200")  // Allow CORS from Angular app

public class PaymentController {

    @Autowired
    private PaymentService paymentService;
    @PostMapping
    public ResponseEntity<Payment> createPayment(@RequestBody Payment payment) {
        // Create the payment record
        Payment createdPayment = paymentService.createPayment(payment);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdPayment);
    }

    @PostMapping("/create")
    public ResponseEntity<Payment> createPayment(@RequestBody PaymentDTO paymentDTO) {
        try {
            // Create a Payment entity from PaymentDTO
            Payment payment = paymentService.createPayment(paymentDTO);
            return new ResponseEntity<>(payment, HttpStatus.CREATED);  // Return 201 if successful
        } catch (Exception e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);  // Return 400 if error occurs
        }
    }
    // http://localhost:2007/payments/create
//    {
//    	  
//        "orderId":54,
//        "paymentMethod": "Card",        
//        "paymentDate": "2024-11-11T00:00:00.000+00:00",
//        "customerId": 1,
//        "totalCost": 1000.0
//    }
//Initially payment status was set to be "Pending"
    // Get all payments
    @GetMapping("/getall")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = paymentService.getAllPayments();
        return ResponseEntity.ok(payments);
    }
  //  http://localhost:2007/payments/getall
    

    // Get payment by ID
    @GetMapping("/getbyid/{id}")
    public ResponseEntity<Payment> getById(@PathVariable Long id) {
        Payment payment = paymentService.getById(id);
        return ResponseEntity.ok(payment);
    }
    // http://localhost:2007/payments/getbyid/1
 // Endpoint to get payment by orderId
//    @GetMapping("/{orderId}")
//    public ResponseEntity<Payment> getPaymentByOrderIdd(@PathVariable Long orderId) {
//        Payment payment = paymentService.getPaymentByOrderId(orderId);
//        if (payment == null) {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if payment not found
//        }
//        return new ResponseEntity<>(payment, HttpStatus.OK);  // Return payment details with HTTP 200
//    }


   //  Endpoint to process payment
    @PostMapping("/process")
//    public ResponseEntity<Payment> processPayment(@RequestBody PaymentDTO paymentDTO) {
//        System.out.println("📥 Received Payment Request: " + paymentDTO);
//
//        if (paymentDTO.getOrderId() == null || paymentDTO.getOrderId() == 0) {
//            System.out.println("❌ Invalid Order ID: " + paymentDTO.getOrderId());
//            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
//        }
//
//        try {
//            Payment payment = paymentService.processPayment(paymentDTO);
//            System.out.println("✅ Payment Processed: " + payment);
//            return new ResponseEntity<>(payment, HttpStatus.CREATED);
//        } catch (Exception e) {
//            System.out.println("❌ Error processing payment: " + e.getMessage());
//            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }

    public ResponseEntity<Payment> processPayment(@RequestBody PaymentDTO paymentDTO) {
        System.out.println("📥 Received Payment Request: " + paymentDTO);

        if (paymentDTO.getOrderId() == null || paymentDTO.getOrderId() == 0) {
            System.out.println("❌ Invalid Order ID: " + paymentDTO.getOrderId());
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            Payment payment = paymentService.processPayment(paymentDTO);
            System.out.println("✅ Payment Processed: " + payment);
            return new ResponseEntity<>(payment, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("❌ Error processing payment: " + e.getMessage());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    
   // http://localhost:2007/payments/process
//    {
//    	  "orderId": 12345,
//    	  "paymentMethod": "Credit Card",
//    	  "totalCost": 250.0,
//    	  "paymentDate": "2024-11-23T10:00:00",
//    	  "paymentStatus": "Pending",
//    	  "customerId":12
//    	}
    
 

  // Endpoint to process payment (called by CustomerService)

    @PostMapping("/make-payment")
    public ResponseEntity<Payment> makePayment(@RequestBody PaymentDTO paymentDTO) {
        try {
            Payment payment = paymentService.processPayment(paymentDTO);
            return new ResponseEntity<>(payment, HttpStatus.CREATED);
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
    }
//  http://localhost:2007/payments/1/make-payment
//  {
//	  "orderId": 12345,
//	  "paymentMethod": "Debit Card",
//	  "totalCost": 250.0,
//	  "paymentDate": "2024-11-23T10:00:00",
//	  "paymentStatus": "Done"
//	}
  

  // Endpoint to get payment by Order ID
//  @GetMapping("/{orderId}")
//  public ResponseEntity<Payment> getPaymentByOrderId(@PathVariable Long orderId) {
//      Payment payment = paymentService.getPaymentByOrderId(orderId);
//      if (payment == null) {
//          return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if payment not found
//      }
//      return new ResponseEntity<>(payment, HttpStatus.OK);  // Return payment details with HTTP 200
//  }
//  http://localhost:2007/payments/1
  

  // Endpoint to update payment status
  @PatchMapping("/{id}/status")
  public ResponseEntity<Payment> updatePaymentStatus(@PathVariable Long id, @RequestParam String status) {
      Payment payment = paymentService.updatePaymentStatus(id, status);
      return new ResponseEntity<>(payment, HttpStatus.OK);
  }
//  http://localhost:2007/payments/1/status?status=Completed
  
  
  //------new change
  // Endpoint to get all payments by customer ID
  @GetMapping("/getbycustomer/{customerId}")
  public ResponseEntity<List<Payment>> getPaymentsByCustomer(@PathVariable Long customerId) {
      List<Payment> payments = paymentService.getPaymentsByCustomer(customerId);
      if (payments.isEmpty()) {
          return new ResponseEntity<>(HttpStatus.NO_CONTENT); // 204 No Content if no payments found
      }
      return new ResponseEntity<>(payments, HttpStatus.OK); // 200 OK
  }

  // Update Payment
  @PutMapping("/{id}")
  public ResponseEntity<Payment> updatePayment(@PathVariable Long id, @RequestBody Payment paymentDetails) {
      Payment updatedPayment = paymentService.updatePayment(id, paymentDetails);
      if (updatedPayment != null) {
          return ResponseEntity.ok(updatedPayment);  // Return updated payment
      }
      return ResponseEntity.notFound().build();  // Return 404 if payment not found
  }

  // Delete Payment
  @DeleteMapping("/{id}")
  public ResponseEntity<Void> deletePayment(@PathVariable Long id) {
      boolean isDeleted = paymentService.deletePayment(id);
      if (isDeleted) {
          return ResponseEntity.noContent().build();  // Return 204 No Content if successful
      }
      return ResponseEntity.notFound().build();  // Return 404 if payment not found
  }
  
  @GetMapping("/{orderId}")
  public ResponseEntity<Payment> getPaymentByOrderId(@PathVariable Long orderId) {
      Payment payment = paymentService.getPaymentByOrderId(orderId);
      if (payment == null) {
          return new ResponseEntity<>(HttpStatus.NOT_FOUND);  // Return 404 if payment not found
      }
      return new ResponseEntity<>(payment, HttpStatus.OK);  // Return payment details with HTTP 200
  }
//  @GetMapping("/payments/{orderId}")
//  public ResponseEntity<Payment> getPaymentDetails(@PathVariable Long orderId) {
//      System.out.println("🔍 Fetching payment details for Order ID: " + orderId);
//      
//      Payment payment = paymentService.getPaymentDetails(orderId);
//
//      return payment.map(ResponseEntity::ok)
//                    .orElseGet(() -> {
//                        System.out.println("❌ Payment Not Found for Order ID: " + orderId);
//                        return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
//                    });
//  }
  
  
}