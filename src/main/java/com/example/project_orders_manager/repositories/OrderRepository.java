package com.example.project_orders_manager.repositories;

import com.example.project_orders_manager.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
