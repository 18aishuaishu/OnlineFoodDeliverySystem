package com.Restaurant_Service.Repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Restaurant_Service.Entity.Restaurant;


@Repository

public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {
	
}
