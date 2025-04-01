package com.example.project_orders_manager.controllers;

import com.example.project_orders_manager.domain.dto.productDTOs.ProductDTO;
import com.example.project_orders_manager.domain.dto.productDTOs.ProductSummaryDTO;
import com.example.project_orders_manager.services.ProductService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/products")
@Tag(name = "Product", description = "Endpoints para gerenciamento de produtos")
public class ProductController {

    @Autowired
    private ProductService service;

    @Operation(summary = "Listar produtos", description = "Retorna uma lista paginada com todos os produtos cadastrados")
    @GetMapping
    public ResponseEntity<Page<ProductSummaryDTO>> getProducts(Pageable pageable) {
        return ResponseEntity.ok(service.getProducts(pageable));
    }

    @Operation(summary = "Buscar produto por ID", description = "Retorna os detalhes de um produto cadastrado pelo ID informado")
    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getProduct(id));
    }

    @GetMapping(params = "SKU")
    public ResponseEntity<ProductDTO> getProductBySKU(@RequestParam String SKU) {
        return ResponseEntity.ok(service.getProductbySKU(SKU));
    }

    @Operation(summary = "Cadastrar novo produto", description = "Cria um novo produto e retorna os dados cadastrados")
    @PostMapping
    public ResponseEntity<ProductDTO> postProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO productSaved = service.postProduct(productDTO);
        URI location = URI.create("/api/v1/products/" + productSaved.id());
        return ResponseEntity.created(location).body(productSaved);
    }

    @Operation(summary = "Atualizar produto", description = "Atualiza os dados de um produto com base no ID informado")
    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable UUID id, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(service.updateProduct(id, productDTO));
    }
}
