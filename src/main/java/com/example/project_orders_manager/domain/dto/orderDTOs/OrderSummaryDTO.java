package com.example.project_orders_manager.domain.dto.orderDTOs;

import com.example.project_orders_manager.domain.entities.Order;
import com.example.project_orders_manager.domain.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderSummaryDTO(
        UUID id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt,
        UUID customerId,
        OrderStatus status,
        BigDecimal total
) {
    public static OrderSummaryDTO fromEntity(Order order) {
        return new OrderSummaryDTO(
                order.getId(),
                order.getCreatedAt(),
                order.getUpdatedAt(),
                order.getCustomer().getId(),
                order.getStatus(),
                order.getTotal()
        );
    }
}
