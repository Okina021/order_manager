package com.example.project_orders_manager.domain.dto.categoryDTOs;

import com.example.project_orders_manager.domain.entities.Category;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;
import java.util.UUID;

public record CategorySummaryDTO(
        UUID id,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime createdAt,
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss")
        LocalDateTime updatedAt,
        String category
) {
    public static CategorySummaryDTO fromEntity(Category category) {
        return new CategorySummaryDTO(
                category.getId(),
                category.getCreatedAt(),
                category.getUpdatedAt(),
                category.getCategory()
        );
    }
}
