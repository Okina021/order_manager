package com.example.project_orders_manager.domain.dto.productDTOs;

import com.example.project_orders_manager.domain.entities.Product;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductSummaryDTO(
        UUID id,
        String SKU,
        String name,
        Integer qty,
        BigDecimal price
) {
    public static ProductSummaryDTO fromEntity(Product product) {
        return new ProductSummaryDTO(
                product.getId(),
                product.getSKU(),
                product.getName(),
                product.getQuantity(),
                product.getPrice()
        );
    }

    public static Product toEntityWithId(ProductSummaryDTO summaryDTO) {
        Product product = new Product();
        product.setId(summaryDTO.id());
        product.setSKU(summaryDTO.SKU());
        product.setName(summaryDTO.name());
        return product;
    }
}
