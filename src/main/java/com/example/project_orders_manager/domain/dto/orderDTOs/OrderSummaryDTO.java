package com.example.project_orders_manager.domain.dto.orderDTOs;

import com.example.project_orders_manager.domain.Order;
import com.example.project_orders_manager.domain.enums.OrderStatus;

public record OrderSummaryDTO(
        Long id,
        OrderStatus status,
        Long customer_id
) {
    public static OrderSummaryDTO fromEntity(Order order){
        return new OrderSummaryDTO(order.getId(), order.getStatus(), order.getCustomer().getId());
    }
}
