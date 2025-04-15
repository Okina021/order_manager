package com.example.project_orders_manager.repositories;

import com.example.project_orders_manager.domain.entities.Order;
import com.example.project_orders_manager.domain.enums.OrderStatus;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface OrderRepository extends JpaRepository<Order, UUID> {
    @Query("SELECT o FROM Order o WHERE o.updatedAt BETWEEN :dateFrom AND :dateTo")
    Page<Order> findByDateBetween(@Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo, Pageable pageable);

    @Query("SELECT o FROM Order o WHERE o.updatedAt <= :dateTo")
    Page<Order> findByDateBefore(@Param("dateTo") LocalDateTime dateTo, Pageable pageable);

    Page<Order> findByStatus(OrderStatus status, Pageable pageable);
}
