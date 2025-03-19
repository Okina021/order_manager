package com.example.project_orders_manager.models.dto;

import com.example.project_orders_manager.models.Customer;
import com.example.project_orders_manager.models.Order;
import com.example.project_orders_manager.models.enums.OrderStatus;

public record OrderDTO(
        Long id,
        OrderStatus status,
        Long customerID
) {
    public static OrderDTO fromEntity(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                order.getCustomer().getId()
        );
    }

    public static Order toEntityWithId(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.id());
        order.setStatus(orderDTO.status());
        Customer customer = new Customer();
        customer.setId(orderDTO.customerID());
        return order;
    }

    public static Order toEntity(OrderDTO orderDTO) {
        Order order = new Order();
        order.setStatus(orderDTO.status());
        Customer customer = new Customer();
        customer.setId(orderDTO.customerID());
        return order;
    }
}
