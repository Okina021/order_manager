package com.example.project_orders_manager.domain.dto.categoryDTOs;

import com.example.project_orders_manager.domain.entities.Category;
import com.example.project_orders_manager.domain.dto.productDTOs.ProductSummaryDTO;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

public record CategoryDTO(
        UUID id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime created_at,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updated_at,
        String category,
        List<ProductSummaryDTO> products
) {

    public static CategoryDTO fromEntity(Category category) {
        return new CategoryDTO(
                category.getId(),
                category.getCreatedAt(),
                category.getUpdatedAt(),
                category.getCategory(),
                category.getProducts().stream().map(ProductSummaryDTO::fromEntity).toList()
        );
    }

    public static Category toEntityWithId(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setId(categoryDTO.id());
        category.setCategory(categoryDTO.category());
        return category;
    }

    public static Category toEntity(CategoryDTO categoryDTO) {
        Category category = new Category();
        category.setCategory(categoryDTO.category());
        return category;
    }
}
 