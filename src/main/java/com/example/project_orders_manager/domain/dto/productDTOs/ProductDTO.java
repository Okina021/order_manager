package com.example.project_orders_manager.domain.dto.productDTOs;

import com.example.project_orders_manager.domain.dto.categoryDTOs.CategorySummaryDTO;
import com.example.project_orders_manager.domain.entities.Product;

import java.math.BigDecimal;
import java.util.UUID;

public record ProductDTO(
        UUID id,
        String SKU,
        String name,
        Integer qty,
        BigDecimal price,
        CategorySummaryDTO category
) {
    public static ProductDTO fromEntity(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getSKU(),
                product.getName(),
                product.getQuantity(),
                product.getPrice(),
                CategorySummaryDTO.fromEntity(product.getCategory())
        );
    }

    public static Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setSKU(productDTO.SKU().toUpperCase());
        product.setName(productDTO.name().toUpperCase());
        product.setQuantity(productDTO.qty());
        product.setPrice(productDTO.price());
        product.setCategory(CategorySummaryDTO.toEntity(productDTO.category()));

        return product;
    }

    public static Product toEntityWithId(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.id());
        product.setSKU(productDTO.SKU());
        product.setName(productDTO.name());
        product.setQuantity(productDTO.qty());
        product.setPrice(productDTO.price());

        return product;
    }
}



