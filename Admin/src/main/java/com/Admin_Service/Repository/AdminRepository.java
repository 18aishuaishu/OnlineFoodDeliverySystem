package com.Admin_Service.Repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.Admin_Service.Entity.Admin;


@Repository
public interface AdminRepository extends JpaRepository<Admin, Long> {
    Admin findByUsername(String username); // to find admin by username (for login)

	Object findUserById(Long id);
}