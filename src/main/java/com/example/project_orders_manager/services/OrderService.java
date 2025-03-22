package com.example.project_orders_manager.services;

import com.example.project_orders_manager.exceptions.BadRequestException;
import com.example.project_orders_manager.models.Order;
import com.example.project_orders_manager.models.dto.orderDTOs.OrderDTO;
import com.example.project_orders_manager.models.dto.orderDTOs.OrderSummaryDTO;
import com.example.project_orders_manager.models.enums.OrderStatus;
import com.example.project_orders_manager.repositories.OrderRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    public List<OrderSummaryDTO> getAllOrders() {
        return repository.findAll().stream().map(OrderSummaryDTO::fromEntity).collect(Collectors.toList());
    }

    public OrderDTO getOrder(Long id) {
        return repository.findById(id).map(OrderDTO::fromEntity).orElseThrow(() -> new EntityNotFoundException("Order id " + id + " not found"));
    }

    public OrderDTO postOrder(OrderDTO order) {
        if (order.id() != null) throw new BadRequestException("New order cannot have an ID.");

        return OrderDTO.fromEntity(repository.save(OrderDTO.toEntity(order)));
    }

    public void deleteOrder(Long id) {
        Order order = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order id " + id + " not found"));
        repository.deleteById(id);
    }

    public OrderDTO changeOrderStatus(Long id, Integer status) {
        Order order = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order id \" + id + \" not found"));
        order.setStatus(OrderStatus.fromCode(status));
        return OrderDTO.fromEntity(repository.save(order));
    }

}
