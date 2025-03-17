package com.example.prova_evo.models.dto;

import com.example.prova_evo.models.Customer;
import com.example.prova_evo.models.Order;
import com.example.prova_evo.models.Product;
import com.example.prova_evo.models.enums.OrderStatus;

import java.util.List;

public record OrderDTO(
        Long id,
        OrderStatus status,
        Long customerID
//        List<Product> items
) {
    public static OrderDTO fromEntity (Order order) {
        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                order.getCustomer().getId()
        );
    }

    public static Order toEntity(OrderDTO orderDTO){
        Order order = new Order();
        order.setId(orderDTO.id());
        order.setStatus(orderDTO.status());
        Customer customer = new Customer();
        customer.setId(orderDTO.customerID());
        return order;
    }
}
