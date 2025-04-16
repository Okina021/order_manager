package com.example.project_orders_manager.domain.dto.addressDTOs;

import com.example.project_orders_manager.domain.entities.Address;
import com.example.project_orders_manager.domain.entities.Customer;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record AddressDTO(
        UUID id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt,
        String street,
        String number,
        String complement,
        String neighborhood,
        String city,
        String state,
        String country,
        String postalCode,
        Boolean principalAddress,
        UUID customerId
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
        address.setCreatedAt(addressDTO.createdAt());
        address.setUpdatedAt(addressDTO.updatedAt());
        address.setStreet(addressDTO.street());
        address.setNumber(addressDTO.number());
        address.setComplement(addressDTO.complement());
        address.setNeighborhood(addressDTO.neighborhood());
        address.setCity(addressDTO.city());
        address.setState(addressDTO.state());
        address.setCountry(addressDTO.country());
        address.setPostalCode(addressDTO.postalCode());
        address.setPrincipalAddress(addressDTO.principalAddress());

        Customer customer = new Customer();
        customer.setId(addressDTO.customerId());
        address.setCustomer(customer);

        return address;
    }
}
