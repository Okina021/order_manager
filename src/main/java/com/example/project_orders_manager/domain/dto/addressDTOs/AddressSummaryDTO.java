package com.example.project_orders_manager.domain.dto.addressDTOs;

import com.example.project_orders_manager.domain.entities.Address;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record AddressSummaryDTO(
        UUID id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime created_at,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updated_at,
        String postal_code,
        String number,
        Boolean principal,
        UUID customer_id
) {
    public static AddressSummaryDTO fromEntity(Address address) {
        return new AddressSummaryDTO(
                address.getId(),
                address.getCreatedAt(),
                address.getUpdatedAt(),
                address.getPostalCode(),
                address.getNumber(),
                address.getPrincipalAddress(),
                address.getCustomer().getId()
        );
    }
}
