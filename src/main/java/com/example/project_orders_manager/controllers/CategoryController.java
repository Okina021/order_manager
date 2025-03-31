package com.example.project_orders_manager.controllers;

import com.example.project_orders_manager.domain.dto.categoryDTOs.CategoryDTO;
import com.example.project_orders_manager.domain.dto.categoryDTOs.CategorySummaryDTO;
import com.example.project_orders_manager.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/categories")
@Tag(name = "Categorias", description = "Gerenciamento de categorias")
public class CategoryController {
    @Autowired
    private CategoryService service;

    @Operation(summary = "Lista categorias", description = "Retorna uma lista paginada com todas as categorias já cadastradas")
    @GetMapping
    public ResponseEntity<Page<CategorySummaryDTO>> getCategories(Pageable pageable) {
        return ResponseEntity.ok(service.findCategories(pageable));
    }

    @Operation(summary = "Busca categoria por ID", description = "Retorna os detalhes de uma categoria específica pelo seu identificador único")
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategory(
            @Parameter(description = "ID da categoria", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Busca categoria por nome", description = "Retorna os detalhes de uma categoria específica pelo seu nome")
    @GetMapping(params = "category")
    public ResponseEntity<CategoryDTO> getByCategoryName(
            @Parameter(description = "Nome da categoria", required = true)
            @RequestParam String category) {
        return ResponseEntity.ok(service.findByCategory(category));
    }

    @Operation(summary = "Cria uma nova categoria", description = "Salva uma nova categoria e retorna os detalhes dela")
    @PostMapping
    public ResponseEntity<CategoryDTO> save(
            @Parameter(description = "Dados da nova categoria", required = true)
            @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategory = service.save(categoryDTO);
        URI location = URI.create("/api/v1/categories/" + savedCategory.id());
        return ResponseEntity.created(location).body(savedCategory);
    }

    @Operation(summary = "Atualiza uma categoria cadastrada", description = "Atualiza e salva uma categoria já cadastrada e retorna no body")
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> update(
            @Parameter(description = "UUID da categoria a ser atualizada", required = true)
            @PathVariable UUID id,

            @Parameter(description = "Dados atualizados da categoria", required = true)
            @RequestBody CategoryDTO categoryDTO
    ) {
        return ResponseEntity.ok(service.update(id, categoryDTO));
    }

    @Operation(
            summary = "Excluir categoria",
            description = "Remove uma categoria existente pelo seu UUID. Não retorna conteúdo."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(
            @Parameter(description = "UUID da categoria a ser excluída", required = true)
            @PathVariable UUID id
    ) {
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }


}
