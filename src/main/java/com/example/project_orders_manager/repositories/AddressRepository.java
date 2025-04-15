package com.example.project_orders_manager.repositories;

import com.example.project_orders_manager.domain.entities.Address;
import com.example.project_orders_manager.domain.entities.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface AddressRepository extends JpaRepository<Address, UUID> {
    @Query("SELECT o FROM Address o WHERE o.updatedAt BETWEEN :dateFrom AND :dateTo")
    Page<Address> findByDateBetween(@Param("dateFrom") LocalDateTime dateFrom, @Param("dateTo") LocalDateTime dateTo, Pageable pageable);

    @Query("SELECT o FROM Address o WHERE o.updatedAt <= :dateTo")
    Page<Address> findByDateBefore(@Param("dateTo") LocalDateTime dateTo, Pageable pageable);
}
