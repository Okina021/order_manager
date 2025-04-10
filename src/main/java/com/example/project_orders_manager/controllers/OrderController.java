package com.example.project_orders_manager.controllers;

import com.example.project_orders_manager.domain.dto.orderDTOs.OrderDTO;
import com.example.project_orders_manager.domain.dto.orderDTOs.OrderSummaryDTO;
import com.example.project_orders_manager.services.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
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

    @Operation(summary = "Listar pedidos", description = "Retorna uma lista paginada com todos os pedidos cadastrados. É possível filtrar por data inicial e final.")
    @ApiResponse(responseCode = "200", description = "Lista de pedidos retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<OrderSummaryDTO>> listOrders(
            @Parameter(description = "Data inicial para filtro (yyyy-MM-dd)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime dateFrom,

            @Parameter(description = "Data final para filtro (yyyy-MM-dd)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime dateTo,

            @Parameter(hidden = true)
            Pageable pageable) {
        return ResponseEntity.ok(service.getAllOrders(dateFrom, dateTo, pageable));
    }

    @Operation(summary = "Buscar pedido por ID", description = "Retorna os detalhes de um pedido pelo UUID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Pedido encontrado",
                    content = @Content(schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderDTO> getOrderById(
            @Parameter(description = "UUID do pedido", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(service.getOrder(id));
    }

    @Operation(summary = "Buscar pedidos por status", description = "Retorna uma lista paginada de pedidos com o status especificado")
    @ApiResponse(responseCode = "200", description = "Pedidos encontrados com sucesso")
    @GetMapping(params = "status")
    public ResponseEntity<Page<OrderSummaryDTO>> getOrdersByStatus(
            @Parameter(description = "Status do pedido", required = true)
            @RequestParam String status,

            @Parameter(hidden = true)
            Pageable pageable) {
        return ResponseEntity.ok(service.getOrderByStatus(status, pageable));
    }

    @Operation(summary = "Cadastrar novo pedido", description = "Cria um novo pedido e retorna os dados cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Pedido criado com sucesso",
                    content = @Content(schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<OrderDTO> createOrder(
            @Parameter(description = "Dados do novo pedido", required = true)
            @Valid @RequestBody OrderDTO order) {
        OrderDTO orderDTO = service.postOrder(order);
        URI location = URI.create("/api/v1/orders/" + orderDTO.id());
        return ResponseEntity.created(location).body(orderDTO);
    }

    @Operation(summary = "Excluir pedido", description = "Remove um pedido existente com base no ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Pedido excluído com sucesso"),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrder(
            @Parameter(description = "UUID do pedido a ser removido", required = true)
            @PathVariable UUID id) {
        service.deleteOrder(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Alterar status do pedido", description = "Atualiza o status de um pedido com base no ID e novo status informados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Status atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = OrderDTO.class))),
            @ApiResponse(responseCode = "404", description = "Pedido não encontrado")
    })
    @PatchMapping("/{id}/status")
    public ResponseEntity<OrderDTO> updateOrderStatus(
            @Parameter(description = "UUID do pedido", required = true)
            @PathVariable UUID id,

            @Parameter(description = "Novo status (numérico)", required = true)
            @RequestParam Integer status) {
        return ResponseEntity.ok(service.changeOrderStatus(id, status));
    }
}
