package com.example.project_orders_manager.repositories;

import com.example.project_orders_manager.domain.entities.Category;
import com.example.project_orders_manager.domain.entities.OrderItem;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, UUID> {
    @Query("SELECT o FROM OrderItem o WHERE o.updatedAt BETWEEN :dateFrom AND :dateTo")
    Page<OrderItem> findByDateBetween(@Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo, Pageable pageable);

    @Query("SELECT o FROM OrderItem o WHERE o.updatedAt <= :dateTo")
    Page<OrderItem> findByDateBefore(@Param("dateTo") LocalDateTime dateTo, Pageable pageable);
}
