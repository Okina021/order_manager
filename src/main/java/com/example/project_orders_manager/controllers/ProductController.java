package com.example.project_orders_manager.controllers;

import com.example.project_orders_manager.domain.dto.orderItemDTOs.OrderItemSummaryDTO;
import com.example.project_orders_manager.domain.dto.productDTOs.ProductDTO;
import com.example.project_orders_manager.domain.dto.productDTOs.ProductSummaryDTO;
import com.example.project_orders_manager.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Product", description = "Endpoints para gerenciamento de produtos")
public class ProductController {

    @Autowired
    private ProductService service;

    @Operation(summary = "Listar produtos", description = "Retorna uma lista paginada com todos os produtos cadastrados. É possível filtrar por data inicial e final.")
    @ApiResponse(responseCode = "200", description = "Lista de produtos retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<ProductSummaryDTO>> listProducts(
            @Parameter(description = "Data inicial para filtro (yyyy-MM-dd)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime dateFrom,

            @Parameter(description = "Data final para filtro (yyyy-MM-dd)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime dateTo,

            @Parameter(hidden = true)
            Pageable pageable) {
        return ResponseEntity.ok(service.listProducts(dateFrom, dateTo, pageable));
    }

    @Operation(summary = "Buscar produto por ID", description = "Retorna os detalhes de um produto cadastrado pelo ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado",
                    content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(
            @Parameter(description = "UUID do produto", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(service.getProduct(id));
    }

    @Operation(summary = "Buscar produto por SKU", description = "Retorna os detalhes de um produto com base no seu código SKU")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto encontrado",
                    content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @GetMapping(params = "SKU")
    public ResponseEntity<ProductDTO> getProductBySKU(
            @Parameter(description = "SKU do produto", required = true)
            @RequestParam String SKU) {
        return ResponseEntity.ok(service.getProductbySKU(SKU));
    }

    @Operation(summary = "Cadastrar novo produto", description = "Cria um novo produto e retorna os dados cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Produto criado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos fornecidos")
    })
    @PostMapping
    public ResponseEntity<ProductDTO> postProduct(
            @Parameter(description = "Dados do novo produto", required = true)
            @RequestBody ProductDTO productDTO) {
        ProductDTO productSaved = service.postProduct(productDTO);
        URI location = URI.create("/api/v1/products/" + productSaved.id());
        return ResponseEntity.created(location).body(productSaved);
    }

    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto com base no ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Produto atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(
            @Parameter(description = "UUID do produto a ser atualizado", required = true)
            @PathVariable UUID id,

            @Parameter(description = "Dados atualizados do produto", required = true)
            @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(service.updateProduct(id, productDTO));
    }

    @Operation(summary = "Deletar produto", description = "Exclui os dados de um produto com base no ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Produto atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = ProductDTO.class))),
            @ApiResponse(responseCode = "404", description = "Produto não encontrado")
    })

    @ApiResponse(responseCode = "204", description = "Produto deletado com sucesso")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProduct(
            @Parameter(description = "UUID do produto a ser deletado", required = true)
            @PathVariable("id") UUID id) {
        service.deleteProduct(id);
        return ResponseEntity.noContent().build();
    }
}
