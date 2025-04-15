package com.example.project_orders_manager.domain.dto.customerDTOs;

import com.example.project_orders_manager.domain.entities.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record CustomerSummaryDTO(
        UUID id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime created_at,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updated_at,
        String name,
        String surname,
        String doc,
        String email
) {
    public static CustomerSummaryDTO fromEntity(Customer customer) {
        return new CustomerSummaryDTO(customer.getId(), customer.getCreatedAt(), customer.getUpdatedAt(), customer.getName(), customer.getSurname(), customer.getDoc(), customer.getEmail());
    }
}
