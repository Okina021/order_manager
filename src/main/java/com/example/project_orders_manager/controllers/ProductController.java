package com.example.project_orders_manager.controllers;

import com.example.project_orders_manager.models.dto.productDTOs.ProductDTO;
import com.example.project_orders_manager.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {
    @Autowired
    private ProductService service;

    @GetMapping
    public ResponseEntity<List<ProductDTO>> getProducts() {
        return ResponseEntity.ok(service.getProducts());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id) {
        return ResponseEntity.ok(service.getProduct(id));
    }

    @PostMapping
    public ResponseEntity<ProductDTO> postProduct(@RequestBody ProductDTO productDTO) {
        ProductDTO DTO = service.postProduct(productDTO);
        URI location = URI.create("/api/v1/products/" + DTO.id());
        return ResponseEntity.created(location).body(DTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO) {
        return ResponseEntity.ok(service.updateProduct(id, productDTO));
    }
}
