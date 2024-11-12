package com.coursemate.repository;

import com.coursemate.model.Administrator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<Administrator, Long> {
    // Add custom query methods if needed
}
