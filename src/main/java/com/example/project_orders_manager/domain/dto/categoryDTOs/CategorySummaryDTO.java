package com.example.project_orders_manager.domain.dto.categoryDTOs;

import com.example.project_orders_manager.domain.entities.Category;

import java.util.UUID;

public record CategorySummaryDTO(UUID id, String category) {
    public static CategorySummaryDTO fromEntity(Category category) {
        return new CategorySummaryDTO(category.getId(), category.getCategory());
    }

    public static Category toEntity(CategorySummaryDTO categorySummaryDTO) {
        Category category = new Category();
        category.setId(categorySummaryDTO.id());
        category.setCategory(category.getCategory());
        return category;
    }

}
