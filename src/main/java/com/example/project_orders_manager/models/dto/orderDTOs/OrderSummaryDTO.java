package com.example.project_orders_manager.models.dto.orderDTOs;

import com.example.project_orders_manager.models.Order;
import com.example.project_orders_manager.models.enums.OrderStatus;

public record OrderSummaryDTO(
        Long id,
        OrderStatus status,
        Long customer_id
) {
    public static OrderSummaryDTO fromEntity(Order order){
        return new OrderSummaryDTO(order.getId(), order.getStatus(), order.getCustomer().getId());
    }
}
