package com.Customer_Service.Reposiory;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Customer_Service.Entity.Customer;

import java.util.Optional;
@Repository
public interface CustomerRepository extends JpaRepository<Customer, Long> {
    Optional<Customer> findById(Long id);  // Correct method for finding by ID
    Optional<Customer> findByName(String name);  // Correct method for finding by name
    Optional<Customer> findByEmail(String email);  // Correct method for finding by email
    // List<Customer> getAllCustomers();

}

