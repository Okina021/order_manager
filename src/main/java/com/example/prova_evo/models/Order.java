package com.example.prova_evo.models;

import com.example.prova_evo.models.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "orders")
@Data
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Enumerated(EnumType.ORDINAL)
    @Column(nullable = false)
    private OrderStatus status = OrderStatus.fromCode(0);
    @ManyToOne
    @JoinColumn(name = "customer_id")
    private Customer customer;
//    @OneToMany(mappedBy = "product")
//    private List<Product> items = new ArrayList<>();

}
