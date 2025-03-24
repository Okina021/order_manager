package com.example.project_orders_manager.domain.dto.orderItemDTOs;

import com.example.project_orders_manager.domain.OrderItem;

import java.math.BigDecimal;

public record OrderItemSummaryDTO(
        Long id,
        Long product_id,
        Integer quantity,
        BigDecimal price,
        BigDecimal total_price) {
    public static OrderItemSummaryDTO fromEntity(OrderItem orderItem) {
        return new OrderItemSummaryDTO(
                orderItem.getId(),
                orderItem.getProduct().getId(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                orderItem.getTotalPrice());
    }
}
