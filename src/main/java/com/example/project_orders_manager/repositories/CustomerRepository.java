package com.example.project_orders_manager.repositories;

import com.example.project_orders_manager.models.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CustomerRepository extends JpaRepository<Customer, Long> {
}
