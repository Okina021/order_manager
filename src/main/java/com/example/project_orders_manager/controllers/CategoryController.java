package com.example.project_orders_manager.controllers;

import com.example.project_orders_manager.domain.dto.categoryDTOs.CategoryDTO;
import com.example.project_orders_manager.domain.dto.categoryDTOs.CategorySummaryDTO;
import com.example.project_orders_manager.services.CategoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    @Operation(summary = "Listar categorias", description = "Retorna uma lista paginada com todas as categorias cadastradas.")
    @ApiResponse(responseCode = "200", description = "Categorias listadas com sucesso")
    @GetMapping
    public ResponseEntity<Page<CategorySummaryDTO>> listCategories(Pageable pageable) {
        return ResponseEntity.ok(service.findCategories(pageable));
    }

    @Operation(summary = "Buscar categoria por ID", description = "Retorna os detalhes de uma categoria pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso",
                    content = @Content(schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CategoryDTO> getCategoryById(
            @Parameter(description = "ID da categoria", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(service.findById(id));
    }

    @Operation(summary = "Buscar categoria por nome", description = "Retorna os detalhes de uma categoria pelo seu nome.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria encontrada com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @GetMapping(params = "category")
    public ResponseEntity<CategoryDTO> getCategoryByName(
            @Parameter(description = "Nome da categoria", required = true)
            @RequestParam String category) {
        return ResponseEntity.ok(service.findByCategory(category));
    }

    @Operation(summary = "Criar categoria", description = "Cria uma nova categoria e retorna seus dados.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Categoria criada com sucesso",
                    content = @Content(schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<CategoryDTO> createCategory(
            @Parameter(description = "Dados da nova categoria", required = true)
            @Valid @RequestBody CategoryDTO categoryDTO) {
        CategoryDTO savedCategory = service.save(categoryDTO);
        URI location = URI.create("/api/v1/categories/" + savedCategory.id());
        return ResponseEntity.created(location).body(savedCategory);
    }

    @Operation(summary = "Atualizar categoria", description = "Atualiza os dados de uma categoria existente.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Categoria atualizada com sucesso",
                    content = @Content(schema = @Schema(implementation = CategoryDTO.class))),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CategoryDTO> updateCategory(
            @Parameter(description = "ID da categoria a ser atualizada", required = true)
            @PathVariable UUID id,

            @Parameter(description = "Novos dados da categoria", required = true)
            @RequestBody CategoryDTO categoryDTO) {
        return ResponseEntity.ok(service.update(id, categoryDTO));
    }

    @Operation(summary = "Excluir categoria", description = "Remove uma categoria existente pelo seu ID.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Categoria removida com sucesso"),
            @ApiResponse(responseCode = "404", description = "Categoria não encontrada")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(
            @Parameter(description = "ID da categoria a ser excluída", required = true)
            @PathVariable UUID id) {
        service.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
