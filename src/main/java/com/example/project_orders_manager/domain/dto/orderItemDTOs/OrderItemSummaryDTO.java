package com.example.project_orders_manager.domain.dto.orderItemDTOs;

import com.example.project_orders_manager.domain.entities.OrderItem;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderItemSummaryDTO(
        UUID id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt,
        UUID productId,
        Integer quantity,
        BigDecimal price,
        BigDecimal subtotal) {
    public static OrderItemSummaryDTO fromEntity(OrderItem orderItem) {
        return new OrderItemSummaryDTO(
                orderItem.getId(),
                orderItem.getCreatedAt(),
                orderItem.getUpdatedAt(),
                orderItem.getProduct().getId(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                orderItem.getSubtotal());
    }
}
