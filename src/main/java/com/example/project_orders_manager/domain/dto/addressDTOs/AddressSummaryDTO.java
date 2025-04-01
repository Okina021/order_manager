package com.example.project_orders_manager.domain.dto.addressDTOs;

import com.example.project_orders_manager.domain.entities.Address;

import java.util.UUID;

public record AddressSummaryDTO(
        UUID id,
        String postal_code,
        String number,
        Boolean principal,
        UUID customer_id
) {
    public static AddressSummaryDTO fromEntity(Address address){
        return  new AddressSummaryDTO(
                address.getId(),
                address.getPostalCode(),
                address.getNumber(),
                address.getPrincipalAddress(),
                address.getCustomer().getId()
        );
    }
}
