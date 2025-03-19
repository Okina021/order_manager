package com.example.project_orders_manager.services;

import com.example.project_orders_manager.models.Product;
import com.example.project_orders_manager.models.dto.ProductDTO;
import com.example.project_orders_manager.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProductService {

    @Autowired
    private ProductRepository repository;

    public List<ProductDTO> getProducts() {
        List<Product> products = repository.findAll();
        return products.stream()
                .map(ProductDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public ProductDTO getProduct(Long id) {
        Optional<Product> productDTO = repository.findById(id);
        if (productDTO.isPresent()) return ProductDTO.fromEntity(productDTO.get());
        throw new RuntimeException("id não encontrado");
    }

    public ProductDTO postProduct(ProductDTO productDTO) {
        Product product = ProductDTO.toEntityWithId(productDTO);
        return ProductDTO.fromEntity(repository.save(product));
    }

    public ProductDTO updateProduct(Long id, ProductDTO product) {
        Optional<Product> optionalProduct = repository.findById(id);

        if (optionalProduct.isPresent()) {
            Product updateProduct = optionalProduct.get();
            if (product.name() != null) updateProduct.setName(product.name());
            if (product.SKU() != null) updateProduct.setSKU(product.SKU());
            if (product.quantity() != null) updateProduct.setQuantity(product.quantity());
            if (product.price() != null) updateProduct.setPrice(product.price());
            return ProductDTO.fromEntity(repository.save(updateProduct));
        }
        throw new RuntimeException("produto não encontrado");
    }

    public void deleteProduct(Long id) {
        Optional<Product> product = repository.findById(id);
        if (product.isPresent())
            repository.delete(product.get());

    }
}
