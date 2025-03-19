package com.example.project_orders_manager.repositories;

import com.example.project_orders_manager.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
