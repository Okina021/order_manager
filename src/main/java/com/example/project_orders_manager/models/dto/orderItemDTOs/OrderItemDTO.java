package com.example.project_orders_manager.models.dto.orderItemDTOs;

import com.example.project_orders_manager.models.Order;
import com.example.project_orders_manager.models.OrderItem;
import com.example.project_orders_manager.models.Product;

import java.math.BigDecimal;

public record OrderItemDTO(
        Long id,
        Long order_id,
        Long product_id,
        Integer quantity,
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
        orderItem.setQuantity(dto.quantity());
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
        orderItem.setQuantity(dto.quantity());
        orderItem.setPrice(dto.price());
        orderItem.setTotalPrice(dto.total_price());
        return orderItem;
    }

}
