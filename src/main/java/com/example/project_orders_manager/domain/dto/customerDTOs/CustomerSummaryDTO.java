package com.example.project_orders_manager.domain.dto.customerDTOs;

import com.example.project_orders_manager.domain.Customer;

public record CustomerSummaryDTO(
        Long id,
        String name,
        String surname,
        String doc,
        String email
) {
    public static CustomerSummaryDTO fromEntity(Customer customer) {
        return new CustomerSummaryDTO(customer.getId(), customer.getName(), customer.getSurname(), customer.getDoc(), customer.getEmail());
    }
}
