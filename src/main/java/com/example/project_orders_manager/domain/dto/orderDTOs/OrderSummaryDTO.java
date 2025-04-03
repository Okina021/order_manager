package com.example.project_orders_manager.domain.dto.orderDTOs;

import com.example.project_orders_manager.domain.entities.Order;
import com.example.project_orders_manager.domain.enums.OrderStatus;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.UUID;

public record OrderSummaryDTO(
        UUID id,
        UUID customer_id,
        OrderStatus status,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime created_at
) {
    public static OrderSummaryDTO fromEntity(Order order) {
        return new OrderSummaryDTO(
                order.getId(),
                order.getCustomer().getId(),
                order.getStatus(),
                order.getCreatedAt()
        );
    }
}
