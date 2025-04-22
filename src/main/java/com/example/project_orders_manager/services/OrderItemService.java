package com.example.project_orders_manager.services;

import com.example.project_orders_manager.domain.dto.addressDTOs.AddressSummaryDTO;
import com.example.project_orders_manager.domain.entities.OrderItem;
import com.example.project_orders_manager.domain.entities.Product;
import com.example.project_orders_manager.domain.dto.orderItemDTOs.OrderItemDTO;
import com.example.project_orders_manager.domain.dto.orderItemDTOs.OrderItemSummaryDTO;
import com.example.project_orders_manager.exceptions.BadRequestException;
import com.example.project_orders_manager.repositories.OrderItemRepository;
import com.example.project_orders_manager.repositories.OrderRepository;
import com.example.project_orders_manager.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
public class OrderItemService {
    @Autowired
    private OrderItemRepository repository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;

    public Page<OrderItemSummaryDTO> listOrderItems(LocalDateTime dateFrom, LocalDateTime dateTo, Pageable pageable) {
        if (dateTo == null) {
            dateTo = LocalDateTime.now();
        }
        if (dateFrom == null) {
            return repository.findByDateBefore(dateTo, pageable).map(OrderItemSummaryDTO::fromEntity);
        }
        return repository.findByDateBetween(dateFrom, dateTo, pageable).map(OrderItemSummaryDTO::fromEntity);
    }

    public OrderItemDTO getOrderItemById(UUID id) {
        return OrderItemDTO.fromEntity(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("OrderItem not found")));
    }

    @Transactional
    public OrderItemDTO create(OrderItemDTO orderItemDTO) {
        if (orderItemDTO.id() != null) {
            throw new BadRequestException("Field id must be null");
        }

        Product product = productRepository.findById(orderItemDTO.productId()).orElseThrow(() -> new EntityNotFoundException("Product not found"));

        orderRepository.findById(orderItemDTO.orderId()).orElseThrow(() -> new EntityNotFoundException("Order not found"));

        if (product.getQuantity() < orderItemDTO.qty())
            throw new BadRequestException("Insufficient stock for product sku:" + product.getSKU());

        product.reduceStock(orderItemDTO.qty());

        OrderItem orderItem = new OrderItem();

        orderItem.setProduct(productRepository.findById(orderItemDTO.productId()).orElseThrow(() -> new EntityNotFoundException("Product not found")));
        orderItem.setOrder(orderRepository.findById(orderItemDTO.orderId()).orElseThrow(() -> new EntityNotFoundException("Order not found")));
        orderItem.setQuantity(orderItemDTO.qty());
        orderItem.setPrice(orderItem.getProduct().getPrice());
        productRepository.save(product);

        repository.save(orderItem);
        productRepository.save(product);

        return OrderItemDTO.fromEntity(orderItem);
    }

    @Transactional
    public OrderItemDTO update(UUID id, OrderItemDTO orderItemDTO) {
        OrderItem orderItem = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order item not found"));

        Product oldProduct = orderItem.getProduct();
        oldProduct.incrementStock(orderItem.getQuantity());

        Product newProduct = productRepository.findById(orderItemDTO.productId())
                .orElseThrow(() -> new EntityNotFoundException("Product not found"));

        orderRepository.findById(orderItemDTO.orderId())
                .orElseThrow(() -> new EntityNotFoundException("Order not found"));

        newProduct.reduceStock(orderItemDTO.qty());

        orderItem.setProduct(newProduct);
        orderItem.setQuantity(orderItemDTO.qty());
        orderItem.setPrice(newProduct.getPrice());

        repository.save(orderItem);
        productRepository.save(oldProduct);
        productRepository.save(newProduct);

        return OrderItemDTO.fromEntity(orderItem);
    }

    @Transactional
    public void delete(UUID id) {
        OrderItem orderItem = repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Order item not found"));

        Product product = orderItem.getProduct();
        product.incrementStock(orderItem.getQuantity());

        repository.delete(orderItem);
        productRepository.save(product);
    }
}

