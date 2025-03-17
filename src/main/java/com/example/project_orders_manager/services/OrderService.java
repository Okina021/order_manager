package com.example.project_orders_manager.services;

import com.example.project_orders_manager.models.Order;
import com.example.project_orders_manager.models.dto.OrderDTO;
import com.example.project_orders_manager.models.enums.OrderStatus;
import com.example.project_orders_manager.repositories.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;

    public List<OrderDTO> getAllOrders() {
        List<Order> orders = repository.findAll();
        List<OrderDTO> orderDTOList = orders.stream()
                .map(OrderDTO::fromEntity)
                .collect(Collectors.toList());
        return orderDTOList;
    }

    public OrderDTO getOrder(Long id) {
        Optional<Order> optionalOrder = repository.findById(id);

        if (optionalOrder.isPresent()) {
            OrderDTO orderDTO = OrderDTO.fromEntity(optionalOrder.get());
            return orderDTO;
        } else {
            throw new RuntimeException("Order not found");
        }
    }

    public OrderDTO postOrder(Order order) {
        repository.save(order);
        return OrderDTO.fromEntity(order);
    }

    public void deleteOrder(Long id) {
        Optional<Order> order = repository.findById(id);
        if (order.isPresent()) {
            repository.delete(order.get());
        } else {
            throw new RuntimeException("erro ao deletar");
        }
    }

    public OrderDTO changeOrderStatus(Long id, Integer status) {
        Optional<Order> order = repository.findById(id);
        if (order.isPresent()) {
            order.get().setStatus(OrderStatus.fromCode(status));
            repository.save(order.get());
            return OrderDTO.fromEntity(order.get());
        } else {
            throw new RuntimeException("Erro ao alterar status");
        }
    }

}
