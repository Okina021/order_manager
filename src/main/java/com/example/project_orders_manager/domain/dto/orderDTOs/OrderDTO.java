package com.example.project_orders_manager.domain.dto.orderDTOs;

import com.example.project_orders_manager.domain.Customer;
import com.example.project_orders_manager.domain.Order;
import com.example.project_orders_manager.domain.dto.orderItemDTOs.OrderItemSummaryDTO;
import com.example.project_orders_manager.domain.enums.OrderStatus;

import java.util.List;

public record OrderDTO(
        Long id,
        OrderStatus status,
        Long customer_id,
        List<OrderItemSummaryDTO> items
) {
    public static OrderDTO fromEntity(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getStatus(),
                order.getCustomer().getId(),
                order.getItems().stream().map(OrderItemSummaryDTO::fromEntity).toList()
        );
    }

    public static Order toEntityWithId(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.id());
        order.setStatus(orderDTO.status());
        Customer customer = new Customer();
        customer.setId(orderDTO.customer_id());
        return order;
    }

    public static Order toEntity(OrderDTO orderDTO) {
        Order order = new Order();
        order.setStatus(orderDTO.status());
        Customer customer = new Customer();
        customer.setId(orderDTO.customer_id());
        return order;
    }
}
