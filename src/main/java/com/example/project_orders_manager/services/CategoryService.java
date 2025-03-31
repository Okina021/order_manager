package com.example.project_orders_manager.services;

import com.example.project_orders_manager.domain.entities.Category;
import com.example.project_orders_manager.domain.dto.categoryDTOs.CategoryDTO;
import com.example.project_orders_manager.domain.dto.categoryDTOs.CategorySummaryDTO;
import com.example.project_orders_manager.repositories.CategoryRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;
import java.util.UUID;

@Service
public class CategoryService {

    @Autowired
    private CategoryRepository repository;

    public Page<CategorySummaryDTO> findCategories(Pageable pageable) {
        return repository.findAll(pageable).map(CategorySummaryDTO::fromEntity);
    }

    public CategoryDTO findById(UUID id) {
        return repository.findById(id).map(CategoryDTO::fromEntity).orElseThrow(() -> new EntityNotFoundException("Category not found"));
    }

    public CategoryDTO findByCategory(String category) {
        return CategoryDTO.fromEntity(repository.findByCategory(category.toUpperCase()).orElseThrow(()-> new EntityNotFoundException("Category not found")));
    }

    @Transactional
    public CategoryDTO save(CategoryDTO categoryDTO){
        Category category = CategoryDTO.toEntity(categoryDTO);
        category.setCategory(category.getCategory().toUpperCase());
        return CategoryDTO.fromEntity(repository.save(category));
    }

    @Transactional
    public CategoryDTO update(UUID id, CategoryDTO categoryDTO){
        Category category = repository.findById(id).orElseThrow(()->new EntityNotFoundException("Category not found"));
        Optional.ofNullable(categoryDTO.category()).ifPresent(category::setCategory);
        return CategoryDTO.fromEntity(repository.save(category));
    }

    public void deleteCategory(UUID id){
        if (!repository.existsById(id)) throw new EntityNotFoundException("Category not found");
        repository.deleteById(id);
    }
}
