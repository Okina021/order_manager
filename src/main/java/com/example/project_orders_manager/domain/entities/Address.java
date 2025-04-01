package com.example.project_orders_manager.domain.entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.UUID;

@Entity
@Table(name = "addresses") // Melhor evitar palavras reservadas no banco
@Data
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @Column(nullable = false)
    @NotBlank(message = "Street is required")
    @Size(max = 255, message = "Street must be at most 255 characters long")
    private String street;

    @Column(nullable = false)
    @NotBlank(message = "Number is required")
    @Size(max = 10, message = "Number must be at most 10 characters long")
    private String number;

    @Size(max = 50, message = "Complement must be at most 50 characters long")
    private String complement;

    @Column(nullable = false)
    @NotBlank(message = "Neighborhood is required")
    @Size(max = 100, message = "Neighborhood must be at most 100 characters long")
    private String neighborhood;

    @Column(nullable = false)
    @NotBlank(message = "City is required")
    @Size(max = 100, message = "City must be at most 100 characters long")
    private String city;

    @Column(nullable = false)
    @NotBlank(message = "State is required")
    @Size(max = 100, message = "State must be at most 100 characters long")
    private String state;

    @Column(nullable = false)
    @NotBlank(message = "Country is required")
    @Size(max = 100, message = "Country must be at most 100 characters long")
    private String country;

    @Column(nullable = false, length = 8)
    @NotBlank(message = "Postal code is required")
    @Size(min = 8, max = 8, message = "Postal code must be exactly 8 digits long")
    @Pattern(regexp = "\\d{8}", message = "Postal code must contain only numbers and be 8 digits long")
    private String postalCode;

    @Column(name = "principal_address")
    private Boolean principalAddress = false;

    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    private Customer customer;
}
