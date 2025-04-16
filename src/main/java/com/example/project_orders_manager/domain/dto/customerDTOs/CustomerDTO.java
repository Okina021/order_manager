package com.example.project_orders_manager.domain.dto.customerDTOs;

import com.example.project_orders_manager.domain.dto.addressDTOs.AddressDTO;
import com.example.project_orders_manager.domain.dto.addressDTOs.BillingAddressDTO;
import com.example.project_orders_manager.domain.dto.orderDTOs.OrderSummaryDTO;
import com.example.project_orders_manager.domain.entities.Customer;
import com.example.project_orders_manager.domain.entities.Order;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public record CustomerDTO(
        UUID id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt,
        String name,
        String surname,
        String doc,
        String email,
        BillingAddressDTO billingAddress,
        List<AddressDTO> shippingAddresses,
        List<OrderSummaryDTO> orders) {

    public static CustomerDTO fromEntity(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getCreatedAt(),
                customer.getUpdatedAt(),
                customer.getName(),
                customer.getSurname(),
                customer.getDoc(),
                customer.getEmail(),
                customer.getBillingAddress() != null ? BillingAddressDTO.fromEntity(customer.getBillingAddress()) : null,
                customer.getShippingAddresses() != null ? customer.getShippingAddresses().stream().map(AddressDTO::fromEntity).collect(Collectors.toList()) : null,
                customer.getOrders() != null ? customer.getOrders().stream().map(OrderSummaryDTO::fromEntity).collect(Collectors.toList()) : null
        );
    }

    public static Customer toEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setId(customerDTO.id());
        customer.setName(customerDTO.name().toUpperCase());
        customer.setSurname(customerDTO.surname().toUpperCase());
        customer.setDoc(String.valueOf(customerDTO.doc()));
        customer.setEmail(customerDTO.email().toLowerCase());

        if (customerDTO.billingAddress() != null) {
            customer.setBillingAddress(BillingAddressDTO.toEntity(customerDTO.billingAddress()));
        }

        if (customerDTO.shippingAddresses() != null) {
            customer.setShippingAddresses(
                    customerDTO.shippingAddresses().stream().map(AddressDTO::toEntity).collect(Collectors.toList())
            );
        }

        if (customerDTO.orders() != null) {
            customer.setOrders(
                    customerDTO.orders().stream().map(orderDTO -> {
                        Order order = new Order();
                        order.setId(orderDTO.id());
                        order.setStatus(orderDTO.status());
                        order.setCustomer(customer);
                        return order;
                    }).collect(Collectors.toList())
            );
        }
        return customer;
    }
}
