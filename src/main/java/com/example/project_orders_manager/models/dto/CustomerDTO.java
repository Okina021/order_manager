package com.example.project_orders_manager.models.dto;

import com.example.project_orders_manager.models.Customer;
import com.example.project_orders_manager.models.Order;

import java.util.List;

public record CustomerDTO(
        Long id,
        String name,
        String surname,
        String doc,
        List<OrderDTO> orders) {

    public static CustomerDTO fromEntity(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getSurname(),
                customer.getDoc(),
                customer.getOrders().stream().map(OrderDTO::fromEntity).toList()
        );
    }

    public static Customer toEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.name());
        customer.setSurname(customerDTO.surname());
        customer.setDoc(customerDTO.doc());
        if (customerDTO.orders() != null){
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
        if (customerDTO.orders() != null){
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
