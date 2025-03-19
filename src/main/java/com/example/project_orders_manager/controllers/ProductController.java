package com.example.project_orders_manager.controllers;

import com.example.project_orders_manager.models.dto.OrderDTO;
import com.example.project_orders_manager.models.dto.ProductDTO;
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
    public ResponseEntity<List<ProductDTO>> getProducts(){
        try {
            return ResponseEntity.ok(service.getProducts());
        }catch (Exception e){
            System.out.println(e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductDTO> getProduct(@PathVariable Long id){
        try{
            return ResponseEntity.ok(service.getProduct(id));
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<ProductDTO> postProduct(@RequestBody ProductDTO productDTO){
        try{
            ProductDTO DTO = service.postProduct(productDTO);
            URI location = URI.create("/api/v1/products/" + DTO.id());
            return ResponseEntity.created(location).body(DTO);
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<ProductDTO> updateProduct(@PathVariable Long id, @RequestBody ProductDTO productDTO){
        try{
            return ResponseEntity.ok(service.updateProduct(id, productDTO));
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }
}
