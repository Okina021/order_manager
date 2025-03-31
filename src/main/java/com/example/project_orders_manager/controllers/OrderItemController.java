package com.example.project_orders_manager.controllers;

import com.example.project_orders_manager.domain.dto.orderItemDTOs.OrderItemDTO;
import com.example.project_orders_manager.domain.dto.orderItemDTOs.OrderItemSummaryDTO;
import com.example.project_orders_manager.services.OrderItemService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order-items")
@Tag(name = "Order Item", description = "Endpoints para gerenciamento de itens de pedido")
public class OrderItemController {

    @Autowired
    private OrderItemService service;

    @Operation(summary = "Listar itens de pedido", description = "Retorna uma lista paginada com todos os itens de pedido cadastrados")
    @GetMapping
    public ResponseEntity<Page<OrderItemSummaryDTO>> getOrderItems(Pageable pageable) {
        return ResponseEntity.ok(service.getOrderItems(pageable));
    }

    @Operation(summary = "Buscar item de pedido por ID", description = "Retorna os detalhes de um item de pedido cadastrado pelo ID informado")
    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDTO> getOrderItemById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getOrderItemById(id));
    }

    @Operation(summary = "Cadastrar novo item de pedido", description = "Cria um novo item de pedido e retorna os dados cadastrados")
    @PostMapping
    public ResponseEntity<OrderItemDTO> postOrderItem(@RequestBody OrderItemDTO orderItemDTO) {
        return ResponseEntity.ok(service.create(orderItemDTO));
    }

    @Operation(
            summary = "Atualizar item de pedido",
            description = "Atualiza um item de pedido existente e retorna os dados atualizados."
    )
    @PutMapping("/{id}")
    public ResponseEntity<OrderItemDTO> updateOrderItem(
            @Parameter(description = "UUID do item de pedido a ser atualizado", required = true)
            @PathVariable UUID id,

            @Parameter(description = "Dados atualizados do item de pedido", required = true)
            @RequestBody OrderItemDTO orderItemDTO
    ) {
        return ResponseEntity.ok(service.update(id, orderItemDTO));
    }

    @Operation(
            summary = "Excluir item de pedido",
            description = "Remove um item de pedido existente pelo seu UUID. Não retorna conteúdo."
    )
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(
            @Parameter(description = "UUID do item de pedido a ser excluído", required = true)
            @PathVariable UUID id
    ) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
