package com.example.project_orders_manager.domain.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.*;

@Entity
@Table(name = "categories")
@Data
public class Category extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(name = "category", nullable = false, unique = true)
    private String category;
    @OneToMany(mappedBy = "category", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Product> products = new ArrayList<>();

    public void setCategory(String category) {
        if (category != null) {
            this.category = category.toUpperCase();
        }
    }
}
