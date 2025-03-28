package com.example.project_orders_manager.services;

import com.example.project_orders_manager.domain.Order;
import com.example.project_orders_manager.domain.OrderItem;
import com.example.project_orders_manager.domain.Product;
import com.example.project_orders_manager.domain.dto.orderDTOs.OrderDTO;
import com.example.project_orders_manager.domain.dto.orderDTOs.OrderSummaryDTO;
import com.example.project_orders_manager.domain.enums.OrderStatus;
import com.example.project_orders_manager.exceptions.BadRequestException;
import com.example.project_orders_manager.repositories.CustomerRepository;
import com.example.project_orders_manager.repositories.OrderItemRepository;
import com.example.project_orders_manager.repositories.OrderRepository;
import com.example.project_orders_manager.repositories.ProductRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class OrderService {
    @Autowired
    private OrderRepository repository;
    @Autowired
    private OrderItemRepository itemRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private CustomerRepository customerRepository;

    public Page<OrderSummaryDTO> getAllOrders(Pageable pageable) {
        return repository.findAll(pageable).map(OrderSummaryDTO::fromEntity);
    }

    public OrderDTO getOrder(UUID id) {
        return repository.findById(id).map(OrderDTO::fromEntity).orElseThrow(() -> new EntityNotFoundException("Order id " + id + " not found"));
    }

    public OrderDTO postOrder(OrderDTO order) {
        if (order.id() != null) throw new BadRequestException("New order cannot have an ID.");
        Order o = new Order();
        o.setCustomer(customerRepository.findById(order.customer_id()).orElseThrow(() -> new EntityNotFoundException("Customer not found")));
        if (order.status() != null) o.setStatus(OrderStatus.PENDING);
        return OrderDTO.fromEntity(repository.save(o));
    }

    public void deleteOrder(UUID id) {
        Order order = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order id " + id + " not found"));
        repository.deleteById(id);
    }

    public OrderDTO changeOrderStatus(UUID id, Integer status) {
        Order order = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Order id " + id + " not found"));
        order.setStatus(OrderStatus.fromCode(status));
        if (order.getStatus() == OrderStatus.CANCELED) {
            for (OrderItem item : order.getItems()) {
                Product product = productRepository.findById(item.getProduct().getId()).orElseThrow(() -> new EntityNotFoundException("Product not found"));
                product.incrementStock(item.getQuantity());
                productRepository.save(product);
            }
        }
        return OrderDTO.fromEntity(repository.save(order));
    }

//    public OrderDTO createOrder(NewOrderDTO newOrderDTO) {
//        Order newOrder = new Order();
//        newOrder.setCustomer(customerRepository.findById(newOrderDTO.customer_id()).orElseThrow(()-> new BadRequestException("Customer not found")));
//
//    }

}
