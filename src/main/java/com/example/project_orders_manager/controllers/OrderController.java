package com.example.project_orders_manager.controllers;

import com.example.project_orders_manager.domain.dto.orderDTOs.OrderDTO;
import com.example.project_orders_manager.domain.dto.orderDTOs.OrderSummaryDTO;
import com.example.project_orders_manager.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
public class OrderController {
    @Autowired
    private OrderService service;

    @GetMapping
    public ResponseEntity<Page<OrderSummaryDTO>> getOrders(Pageable pageable) {
        return ResponseEntity.ok(service.getAllOrders(pageable));
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getOrder(id));
    }

    @PostMapping
    public ResponseEntity<OrderDTO> postCustomer(@RequestBody OrderDTO order) {
        URI location = URI.create("/api/v1/orders" + order.id());
        OrderDTO orderDTO = service.postOrder(order);
        return ResponseEntity.created(location).body(orderDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        service.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderDTO> changeStatus(@PathVariable UUID id, @RequestParam Integer status) {
        return ResponseEntity.ok(service.changeOrderStatus(id, status));
    }


}
