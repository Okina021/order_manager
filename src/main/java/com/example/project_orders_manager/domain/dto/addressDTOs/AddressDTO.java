package com.example.project_orders_manager.domain.dto.addressDTOs;

import com.example.project_orders_manager.domain.entities.Address;
import com.example.project_orders_manager.domain.entities.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record AddressDTO(
        UUID id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime created_at,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updated_at,
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String country,
        String postal_code,
        Boolean principal_address,
        UUID customer_id
) {
    public static AddressDTO fromEntity(Address address) {
        return new AddressDTO(
                address.getId(),
                address.getCreatedAt(),
                address.getUpdatedAt(),
                address.getStreet(),
                address.getNumber(),
                address.getComplement(),
                address.getNeighborhood(),
                address.getCity(),
                address.getState(),
                address.getCountry(),
                address.getPostalCode(),
                address.getPrincipalAddress(),
                address.getCustomer().getId()
        );
    }

    public static Address toEntity(AddressDTO addressDTO) {
        Address address = new Address();
        address.setId(addressDTO.id());
        address.setCreatedAt(addressDTO.created_at());
        address.setUpdatedAt(addressDTO.updated_at());
        address.setStreet(addressDTO.street());
        address.setNumber(addressDTO.number());
        address.setComplement(addressDTO.complement());
        address.setNeighborhood(addressDTO.neighborhood());
        address.setCity(addressDTO.city());
        address.setState(addressDTO.state());
        address.setCountry(addressDTO.country());
        address.setPostalCode(addressDTO.postal_code());
        address.setPrincipalAddress(addressDTO.principal_address());
        Customer customer = new Customer();
        customer.setId(addressDTO.customer_id());
        address.setCustomer(customer);
        return address;
    }
}
