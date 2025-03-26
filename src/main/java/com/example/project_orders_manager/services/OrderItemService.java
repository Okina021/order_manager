package com.example.project_orders_manager.services;

import com.example.project_orders_manager.domain.OrderItem;
import com.example.project_orders_manager.domain.Product;
import com.example.project_orders_manager.domain.dto.orderItemDTOs.OrderItemDTO;
import com.example.project_orders_manager.exceptions.BadRequestException;
import com.example.project_orders_manager.repositories.OrderItemRepository;
import com.example.project_orders_manager.repositories.OrderRepository;
import com.example.project_orders_manager.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository repository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    public List<OrderItemDTO> getOrderItems() {
        return repository.findAll().stream().map(OrderItemDTO::fromEntity).toList();
    }

    public OrderItemDTO getOrderItemById(Long id) {
        return OrderItemDTO.fromEntity(repository.findById(id).orElseThrow());
    }

    @Transactional
    public OrderItemDTO create(OrderItemDTO orderItemDTO) {
        if (orderItemDTO.id() != null) {
            throw new BadRequestException("Field id must be null");
        }

        Product product = productRepository.findById(orderItemDTO.product_id()).orElseThrow(() -> new EntityNotFoundException("Product not found"));

        orderRepository.findById(orderItemDTO.order_id()).orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (product.getQuantity() < orderItemDTO.quantity())
            throw new BadRequestException("Insufficient stock for product sku:" + product.getSKU());

        product.reduceStock(orderItemDTO.quantity());

        OrderItem orderItem = new OrderItem();

        orderItem.setProduct(productRepository.findById(orderItemDTO.product_id()).orElseThrow(() -> new EntityNotFoundException("Product not found")));
        orderItem.setOrder(orderRepository.findById(orderItemDTO.order_id()).orElseThrow(() -> new EntityNotFoundException("Order not found")));
        orderItem.setQuantity(orderItemDTO.quantity());
        orderItem.setPrice(orderItem.getProduct().getPrice());
        productRepository.save(product);

        repository.save(orderItem);
        productRepository.save(product);

        return OrderItemDTO.fromEntity(orderItem);
    }
}
