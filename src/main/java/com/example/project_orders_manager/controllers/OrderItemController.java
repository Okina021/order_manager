package com.example.project_orders_manager.controllers;

import com.example.project_orders_manager.domain.dto.orderItemDTOs.OrderItemDTO;
import com.example.project_orders_manager.services.OrderItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/order-item")
public class OrderItemController {
    @Autowired
    private OrderItemService service;

    @GetMapping
    public ResponseEntity<List<OrderItemDTO>> getOrderItems(){
        return ResponseEntity.ok(service.getOrderItems());
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDTO> getOrderItemById(@PathVariable Long id){
        return ResponseEntity.ok(service.getOrderItemById(id));
    }

    @PostMapping
    public ResponseEntity<OrderItemDTO> postOrderItem(@RequestBody OrderItemDTO orderItemDTO){
        return ResponseEntity.ok(service.create(orderItemDTO));
    }
}
