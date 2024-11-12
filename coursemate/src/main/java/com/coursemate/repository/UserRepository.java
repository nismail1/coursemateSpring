package com.coursemate.repository;

import com.coursemate.model.SystemUser;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<SystemUser, Long> {
    SystemUser findByEmail(String email); // Optional: You may want to find a user by email for login purposes
}
