package com.example.project_orders_manager.domain.dto.orderDTOs;

import com.example.project_orders_manager.domain.dto.orderItemDTOs.OrderItemDTO;
import com.example.project_orders_manager.domain.dto.orderItemDTOs.OrderItemSummaryDTO;
import com.example.project_orders_manager.domain.entities.Customer;
import com.example.project_orders_manager.domain.entities.Order;
import com.example.project_orders_manager.domain.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

public record OrderDTO(
        UUID id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt,
        UUID customerId,
        OrderStatus status,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        List<OrderItemSummaryDTO> items,
        BigDecimal total
) {
    public static OrderDTO fromEntity(Order order) {
        return new OrderDTO(
                order.getId(),
                order.getCreatedAt(),
                order.getUpdatedAt(),
                order.getCustomer().getId(),
                order.getStatus(),
                order.getItems() != null ? order.getItems().stream().map(OrderItemSummaryDTO::fromEntity).toList() : Collections.emptyList(),
                order.getTotal()
        );
    }

    public static Order toEntity(OrderDTO orderDTO) {
        Order order = new Order();
        order.setStatus(orderDTO.status());
        order.setTotal(orderDTO.total());
        order.setCreatedAt(orderDTO.createdAt());
        order.setUpdatedAt(orderDTO.updatedAt());
        Customer customer = new Customer();
        customer.setId(orderDTO.customerId());
        return order;
    }
}
