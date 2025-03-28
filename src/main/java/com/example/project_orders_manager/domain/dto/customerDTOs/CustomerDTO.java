package com.example.project_orders_manager.domain.dto.customerDTOs;

import com.example.project_orders_manager.domain.Customer;
import com.example.project_orders_manager.domain.Order;
import com.example.project_orders_manager.domain.dto.orderDTOs.OrderSummaryDTO;

import java.util.List;
import java.util.UUID;

public record CustomerDTO(
        UUID id,
        String name,
        String surname,
        String doc,
        String email,
        List<OrderSummaryDTO> orders) {

    public static CustomerDTO fromEntity(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getSurname(),
                customer.getDoc(),
                customer.getEmail(),
                customer.getOrders().stream().map(OrderSummaryDTO::fromEntity).toList()
        );
    }

    public static Customer toEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.name().toUpperCase());
        customer.setSurname(customerDTO.surname().toUpperCase());
        customer.setDoc(String.valueOf(customerDTO.doc()));
        customer.setEmail(customerDTO.email().toLowerCase());
        if (customerDTO.orders() != null) {
            customer.setOrders(
                    customerDTO.orders.stream().map(orderDTO -> {
                        Order order = new Order();
                        order.setId(orderDTO.id());
                        order.setStatus(orderDTO.status());
                        order.setCustomer(customer);
                        return order;
                    }).toList()
            );
        }
        return customer;
    }

    public static Customer toEntityWithId(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setId(customerDTO.id());
        customer.setName(customerDTO.name());
        customer.setSurname(customerDTO.surname());
        customer.setDoc(customerDTO.doc());
        customer.setEmail(customerDTO.email());
        if (customerDTO.orders() != null) {
            customer.setOrders(
                    customerDTO.orders.stream().map(orderDTO -> {
                        Order order = new Order();
                        order.setId(orderDTO.id());
                        order.setStatus(orderDTO.status());
                        order.setCustomer(customer);
                        return order;
                    }).toList()
            );
        }
        return customer;
    }
}
