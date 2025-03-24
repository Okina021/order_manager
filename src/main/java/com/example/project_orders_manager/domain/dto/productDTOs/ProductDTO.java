package com.example.project_orders_manager.domain.dto.productDTOs;

import com.example.project_orders_manager.domain.Product;

import java.math.BigDecimal;

public record ProductDTO(
Long id,
String SKU,
String name,
Integer quantity,
BigDecimal price
) {
    public static ProductDTO fromEntity(Product product){
        return new ProductDTO(
                product.getId(),
                product.getSKU(),
                product.getName(),
                product.getQuantity(),
                product.getPrice());
    }

    public static Product toEntity(ProductDTO productDTO){
        Product product = new Product();
        product.setSKU(productDTO.SKU());
        product.setName(productDTO.name());
        product.setQuantity(productDTO.quantity());
        product.setPrice(productDTO.price());

        return product;
    }

    public static Product toEntityWithId(ProductDTO productDTO){
        Product product = new Product();
        product.setId(productDTO.id());
        product.setSKU(productDTO.SKU());
        product.setName(productDTO.name());
        product.setQuantity(productDTO.quantity());
        product.setPrice(productDTO.price());

        return product;
    }
}
