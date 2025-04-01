package com.example.project_orders_manager.controllers;

import com.example.project_orders_manager.domain.dto.orderDTOs.OrderDTO;
import com.example.project_orders_manager.domain.dto.orderDTOs.OrderSummaryDTO;
import com.example.project_orders_manager.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/orders")
@Tag(name = "Order", description = "Endpoints para gerenciamento de pedidos")
public class OrderController {

    @Autowired
    private OrderService service;

    @Operation(summary = "Listar pedidos", description = "Retorna uma lista paginada com todos os pedidos cadastrados")
    @GetMapping
    public ResponseEntity<Page<OrderSummaryDTO>> getOrders(
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime dateTo,
            Pageable pageable) {
        return ResponseEntity.ok(service.getAllOrders(dateFrom, dateTo, pageable));
    }

    @Operation(summary = "Buscar pedido por ID", description = "Retorna os detalhes de um pedido cadastrado pelo ID informado")
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getOrder(id));
    }

    @Operation(summary = "Cadastrar novo pedido", description = "Cria um novo pedido e retorna os dados cadastrados")
    @PostMapping
    public ResponseEntity<OrderDTO> postOrder(@RequestBody OrderDTO order) {
        OrderDTO orderDTO = service.postOrder(order);
        URI location = URI.create("/api/v1/orders/" + orderDTO.id());
        return ResponseEntity.created(location).body(orderDTO);
    }

    @Operation(summary = "Excluir pedido", description = "Remove um pedido cadastrado com base no ID informado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(@PathVariable UUID id) {
        service.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Alterar status do pedido", description = "Atualiza o status de um pedido com base no ID e no novo status informado")
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderDTO> changeStatus(@PathVariable UUID id, @RequestParam Integer status) {
        return ResponseEntity.ok(service.changeOrderStatus(id, status));
    }
}
