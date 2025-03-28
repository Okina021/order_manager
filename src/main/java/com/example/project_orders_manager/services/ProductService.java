package com.example.project_orders_manager.services;

import com.example.project_orders_manager.domain.Product;
import com.example.project_orders_manager.domain.dto.productDTOs.ProductDTO;
import com.example.project_orders_manager.exceptions.BadRequestException;
import com.example.project_orders_manager.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public Page<ProductDTO> getProducts(Pageable pageable) {
        return repository.findAll(pageable)
                .map(ProductDTO::fromEntity);
    }

    public ProductDTO getProduct(UUID id) {
        return repository.findById(id).map((ProductDTO::fromEntity)).orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not foud"));
    }

    public ProductDTO postProduct(ProductDTO productDTO) {
        if (productDTO.id() != null) throw new BadRequestException("Product id must be null");
        return ProductDTO.fromEntity(repository.save(ProductDTO.toEntity(productDTO)));
    }

    public ProductDTO updateProduct(UUID id, ProductDTO product) {
        Product p = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Product with id " + id + " not found"));
        Optional.ofNullable(product.SKU()).ifPresent(p::setSKU);
        Optional.ofNullable(product.price()).ifPresent(p::setPrice);
        Optional.ofNullable(product.name()).ifPresent(p::setName);
        Optional.ofNullable(product.qty()).ifPresent(p::setQuantity);
        return ProductDTO.fromEntity(repository.save(p));
    }

    public void deleteProduct(UUID id) {
        Optional<Product> product = repository.findById(id);
        product.ifPresent(value -> repository.delete(value));
    }
}
