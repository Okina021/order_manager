package com.example.project_orders_manager.controllers;

import com.example.project_orders_manager.models.Order;
import com.example.project_orders_manager.models.dto.OrderDTO;
import com.example.project_orders_manager.services.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/orders")
public class OrdersController {
    @Autowired
    private OrderService service;

    @GetMapping
    public ResponseEntity<List<OrderDTO>> getOrders() {
        try {
            return ResponseEntity.ok(service.getAllOrders());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable Long id) {
        try {
            return ResponseEntity.ok(service.getOrder(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PostMapping
    public ResponseEntity<OrderDTO> postCustomer(@RequestBody Order order) {
        try {
            URI location = URI.create("/api/v1/orders" + order.getId());
            OrderDTO orderDTO = service.postOrder(order);
            return ResponseEntity.created(location).body(orderDTO);
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteOrder(@PathVariable Long id) {
        try {
            service.deleteOrder(id);
            return ResponseEntity.noContent().build();
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderDTO> changeStatus(@PathVariable Long id, @RequestParam Integer status) {
        try {
            OrderDTO order = service.changeOrderStatus(id, status);
            return ResponseEntity.ok(order);
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        } catch (Exception e) {
            return ResponseEntity.internalServerError().build();
        }
    }

}
