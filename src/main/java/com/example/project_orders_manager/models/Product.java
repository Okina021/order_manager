package com.example.project_orders_manager.models;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "product")
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(unique = true, nullable = false)
    private String SKU;
    @Column(nullable = false)
    private String name;
    @Column(name = "qty", nullable = false)
    private Integer quantity = 0;
    @Column(precision = 10, scale = 2)
    private BigDecimal price = BigDecimal.valueOf(0.00);

}
//    public void updateQuantity(Integer quantity){
//        this.setQuantity(quantity);
//    }
