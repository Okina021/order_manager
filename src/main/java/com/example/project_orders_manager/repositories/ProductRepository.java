package com.example.project_orders_manager.repositories;

import com.example.project_orders_manager.domain.entities.OrderItem;
import com.example.project_orders_manager.domain.entities.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface ProductRepository extends JpaRepository<Product, UUID> {
    Optional<Product> findBySKU(String SKU);
    @Query("SELECT o FROM Product o WHERE o.updatedAt BETWEEN :dateFrom AND :dateTo")
    Page<Product> findByDateBetween(@Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo, Pageable pageable);

    @Query("SELECT o FROM Product o WHERE o.updatedAt <= :dateTo")
    Page<Product> findByDateBefore(@Param("dateTo") LocalDateTime dateTo, Pageable pageable);
}
