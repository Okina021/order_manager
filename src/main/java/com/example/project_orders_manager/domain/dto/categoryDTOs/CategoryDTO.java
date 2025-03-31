package com.example.project_orders_manager.domain.dto.categoryDTOs;

import com.example.project_orders_manager.domain.entities.Category;
import com.example.project_orders_manager.domain.dto.productDTOs.ProductSummaryDTO;

import java.util.List;
import java.util.UUID;

public record CategoryDTO(
        UUID id,
        String category,
        List<ProductSummaryDTO> products
) {

    public static CategoryDTO fromEntity(Category category) {
        return new CategoryDTO(
                category.getId(),
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
 