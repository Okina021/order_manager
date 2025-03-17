package com.example.prova_evo.repositories;

import com.example.prova_evo.models.Order;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Order, Long> {
}
