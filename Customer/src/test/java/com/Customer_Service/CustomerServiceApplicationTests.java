package com.Customer_Service;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.Customer_Service.Service.CustomerService;

@SpringBootTest
class CustomerServiceApplicationTests {

	@Autowired
	    private CustomerService customerService;
	@Test
	void contextLoads() {
	}

}
