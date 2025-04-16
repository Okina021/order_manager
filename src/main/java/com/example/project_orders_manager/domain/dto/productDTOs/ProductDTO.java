package com.example.project_orders_manager.domain.dto.productDTOs;

import com.example.project_orders_manager.domain.dto.categoryDTOs.CategorySummaryDTO;
import com.example.project_orders_manager.domain.entities.Category;
import com.example.project_orders_manager.domain.entities.Product;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

public record ProductDTO(
        UUID id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt,
        String SKU,
        String name,
        Integer qty,
        BigDecimal price,
        String category
) {
    public static ProductDTO fromEntity(Product product) {
        return new ProductDTO(
                product.getId(),
                product.getCreatedAt(),
                product.getUpdatedAt(),
                product.getSKU(),
                product.getName(),
                product.getQuantity(),
                product.getPrice(),
                product.getCategory().getCategory()
        );
    }

    public static Product toEntity(ProductDTO productDTO) {
        Product product = new Product();
        product.setId(productDTO.id());
        product.setCreatedAt(productDTO.createdAt());
        product.setUpdatedAt(productDTO.updatedAt());
        product.setSKU(productDTO.SKU().toUpperCase());
        product.setName(productDTO.name().toUpperCase());
        product.setQuantity(productDTO.qty());
        product.setPrice(productDTO.price());
        Category category1 = new Category();
        category1.setCategory(productDTO.category());
        product.setCategory(category1);

        return product;
    }


}



