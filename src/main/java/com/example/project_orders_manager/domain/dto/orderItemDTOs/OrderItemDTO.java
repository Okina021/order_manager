package com.example.project_orders_manager.domain.dto.orderItemDTOs;

import com.example.project_orders_manager.domain.entities.Order;
import com.example.project_orders_manager.domain.entities.OrderItem;
import com.example.project_orders_manager.domain.entities.Product;

import java.math.BigDecimal;
import java.util.UUID;

public record OrderItemDTO(
        UUID id,
        UUID order_id,
        UUID product_id,
        Integer qty,
        BigDecimal price,
        BigDecimal total_price
) {

    public static OrderItemDTO fromEntity(OrderItem orderItem) {
        return new OrderItemDTO(
                orderItem.getId(),
                orderItem.getOrder().getId(),
                orderItem.getProduct().getId(),
                orderItem.getQuantity(),
                orderItem.getPrice(),
                orderItem.getTotalPrice()
        );
    }

    public static OrderItem toEntityWithId(OrderItemDTO dto) {
        OrderItem orderItem = new OrderItem();
        orderItem.setId(dto.id());
        Order order = new Order();
        order.setId(dto.order_id());
        Product product = new Product();
        product.setId(dto.product_id());
        orderItem.setQuantity(dto.qty());
        orderItem.setPrice(dto.price());
        orderItem.setTotalPrice(dto.total_price());
        return orderItem;
    }

    public static OrderItem toEntity(OrderItemDTO dto) {
        OrderItem orderItem = new OrderItem();
        Order order = new Order();
        order.setId(dto.order_id());
        Product product = new Product();
        product.setId(dto.product_id());
        orderItem.setQuantity(dto.qty());
        orderItem.setPrice(dto.price());
        orderItem.setTotalPrice(dto.total_price());
        return orderItem;
    }

}
