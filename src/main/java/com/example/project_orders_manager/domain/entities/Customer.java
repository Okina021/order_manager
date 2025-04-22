package com.example.project_orders_manager.domain.entities;

import com.example.project_orders_manager.domain.enums.CustomerType;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import jakarta.persistence.*;
import jakarta.validation.constraints.Pattern;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name = "customers")
@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonIdentityInfo(generator = ObjectIdGenerators.PropertyGenerator.class, property = "id")
public class Customer extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;
    @Column(nullable = false)
    private String name;
    @Column(nullable = false)
    private String surname;
    @Pattern(regexp = "\\d{11}|\\d{14}", message = "O documento deve conter 11 ou 14 dígitos numéricos")
    @Column(length = 14, unique = true, nullable = false)
    private String doc;
    @Column(name = "type")
    @Enumerated(EnumType.STRING)
    private CustomerType type;
    @Column(unique = true, nullable = false)
    private String email;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "billing_address_id")
    private Address billingAddress;
    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Address> shippingAddresses;

    @OneToMany(mappedBy = "customer", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Order> orders = new ArrayList<>();


    public void setName(String name) {
        this.name = name.toUpperCase();
    }

    public void setSurname(String surname) {
        this.surname = surname.toUpperCase();
    }

    public void setEmail(String email) {
        this.email = email.toLowerCase();
    }
}
