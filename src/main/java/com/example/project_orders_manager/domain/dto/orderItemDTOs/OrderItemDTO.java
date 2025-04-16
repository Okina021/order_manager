package com.example.project_orders_manager.domain.dto.orderItemDTOs;

import com.example.project_orders_manager.domain.entities.Order;
import com.example.project_orders_manager.domain.entities.OrderItem;
import com.example.project_orders_manager.domain.entities.Product;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record OrderItemDTO(
        UUID id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt,
        UUID orderId,
        UUID productId,
        Integer qty,
        BigDecimal price,
        BigDecimal subtotal
) {

    public static OrderItemDTO fromEntity(OrderItem orderItem) {
        return new OrderItemDTO(
                orderItem.getId(),
                orderItem.getCreatedAt(),
                orderItem.getUpdatedAt(),
                orderItem.getOrder().getId(),
                orderItem.getProduct().getId(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                orderItem.getSubtotal()
        );
    }

    public static OrderItem toEntity(OrderItemDTO dto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(dto.id());
        Order order = new Order();
        order.setId(dto.orderId());
        Product product = new Product();
        product.setId(dto.productId());
        orderItem.setQuantity(dto.qty());
        orderItem.setPrice(dto.price());
        orderItem.setSubtotal(dto.subtotal());
        return orderItem;
    }

}
