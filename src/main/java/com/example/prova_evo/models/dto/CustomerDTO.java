package com.example.prova_evo.models.dto;

import com.example.prova_evo.models.Customer;
import com.example.prova_evo.models.Order;

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
