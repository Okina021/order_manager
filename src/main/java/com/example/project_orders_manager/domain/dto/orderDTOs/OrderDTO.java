package com.example.project_orders_manager.domain.dto.orderDTOs;

import com.example.project_orders_manager.domain.entities.Customer;
import com.example.project_orders_manager.domain.entities.Order;
import com.example.project_orders_manager.domain.dto.orderItemDTOs.OrderItemSummaryDTO;
import com.example.project_orders_manager.domain.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public record OrderDTO(
        UUID id,
        UUID customer_id,
        OrderStatus status,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime created_at,
        List<OrderItemSummaryDTO> items
) {
    public static OrderDTO fromEntity(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getCustomer().getId(),
                order.getStatus(),
                order.getCreatedAt(),
                order.getItems() != null ? order.getItems().stream().map(OrderItemSummaryDTO::fromEntity).toList() : Collections.emptyList()
        );
    }

    public static Order toEntityWithId(OrderDTO orderDTO) {
        Order order = new Order();
        order.setId(orderDTO.id());
        order.setStatus(orderDTO.status());
        Customer customer = new Customer();
        customer.setId(orderDTO.customer_id());
        order.setCreatedAt(orderDTO.created_at());
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
