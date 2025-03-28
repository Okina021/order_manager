package com.example.project_orders_manager.domain;

import com.example.project_orders_manager.exceptions.BadRequestException;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.UUID;

@Entity
@Table(name = "products")
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Product implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(unique = true, nullable = false)
    private String SKU;
    @Column(nullable = false)
    private String name;
    @Column(name = "qty", nullable = false)
    private Integer quantity = 0;
    @Column(precision = 10, scale = 2)
    private BigDecimal price = BigDecimal.valueOf(0.00);

    public void reduceStock(Integer quantity){
        if (this.quantity< quantity) throw new BadRequestException("Insufficient stock for the product: SKU " + this.SKU);
        this.quantity -= quantity;
    }

    public void incrementStock(Integer quantity){
        if (quantity < 0) throw new BadRequestException("Cannot increment stock with a negative value: SKU " + this.SKU);
        this.quantity += quantity;
    }

}
