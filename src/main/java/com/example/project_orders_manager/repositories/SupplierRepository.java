package com.example.project_orders_manager.repositories;

import com.example.project_orders_manager.domain.entities.Supplier;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

public interface SupplierRepository extends JpaRepository<Supplier, UUID>, JpaSpecificationExecutor<Supplier> {
    @Query("SELECT o FROM Supplier o WHERE o.updatedAt BETWEEN :dateFrom AND :dateTo")
    Page<Supplier> findByDateBetween(@Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo, Pageable pageable);

    @Query("SELECT o FROM Supplier o WHERE o.updatedAt <= :dateTo")
    Page<Supplier> findByDateBefore(@Param("dateTo") LocalDateTime dateTo, Pageable pageable);

    Optional<Supplier> findByCnpj(String cnpj);
}
