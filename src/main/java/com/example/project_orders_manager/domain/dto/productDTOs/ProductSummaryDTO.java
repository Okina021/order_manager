package com.example.project_orders_manager.domain.dto.productDTOs;

import com.example.project_orders_manager.domain.entities.Product;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductSummaryDTO(
        UUID id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime created_at,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updated_at,
        String SKU,
        String name,
        Integer qty,
        BigDecimal price
) {
    public static ProductSummaryDTO fromEntity(Product product) {
        return new ProductSummaryDTO(
                product.getId(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getSKU(),
                product.getName(),
                product.getQuantity(),
                product.getPrice()
        );
    }
}
