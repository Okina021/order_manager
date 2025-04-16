package com.example.project_orders_manager.controllers;

import com.example.project_orders_manager.domain.dto.addressDTOs.AddressSummaryDTO;
import com.example.project_orders_manager.domain.dto.orderItemDTOs.OrderItemDTO;
import com.example.project_orders_manager.domain.dto.orderItemDTOs.OrderItemSummaryDTO;
import com.example.project_orders_manager.services.OrderItemService;
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

import java.time.LocalDateTime;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/order-items")
@Tag(name = "Order Item", description = "Endpoints para gerenciamento de itens de pedido")
public class OrderItemController {

    @Autowired
    private OrderItemService service;

    @Operation(summary = "Listar itens de pedido", description = "Retorna uma lista paginada com todos os itens de pedidos cadastrados. É possível filtrar por data inicial e final.")
    @ApiResponse(responseCode = "200", description = "Lista de itens de pedido retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<OrderItemSummaryDTO>> listOrderItems(
            @Parameter(description = "Data inicial para filtro (yyyy-MM-dd)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime dateFrom,

            @Parameter(description = "Data final para filtro (yyyy-MM-dd)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime dateTo,

            @Parameter(hidden = true)
            Pageable pageable) {
        return ResponseEntity.ok(service.listOrderItems(dateFrom, dateTo, pageable));
    }

    @Operation(summary = "Buscar item de pedido por ID", description = "Retorna os detalhes de um item de pedido pelo UUID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item de pedido encontrado",
                    content = @Content(schema = @Schema(implementation = OrderItemDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item de pedido não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<OrderItemDTO> getOrderItemById(
            @Parameter(description = "UUID do item de pedido", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(service.getOrderItemById(id));
    }

    @Operation(summary = "Cadastrar novo item de pedido", description = "Cria um novo item de pedido e retorna os dados cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item criado com sucesso",
                    content = @Content(schema = @Schema(implementation = OrderItemDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<OrderItemDTO> postOrderItem(
            @Parameter(description = "Dados do novo item de pedido", required = true)
            @Valid @RequestBody OrderItemDTO orderItemDTO) {
        return ResponseEntity.ok(service.create(orderItemDTO));
    }

    @Operation(summary = "Atualizar item de pedido", description = "Atualiza um item de pedido existente e retorna os dados atualizados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Item atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = OrderItemDTO.class))),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<OrderItemDTO> updateOrderItem(
            @Parameter(description = "UUID do item de pedido a ser atualizado", required = true)
            @PathVariable UUID id,

            @Parameter(description = "Dados atualizados do item de pedido", required = true)
            @RequestBody OrderItemDTO orderItemDTO) {
        return ResponseEntity.ok(service.update(id, orderItemDTO));
    }

    @Operation(summary = "Excluir item de pedido", description = "Remove um item de pedido existente com base no UUID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Item removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Item não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderItem(
            @Parameter(description = "UUID do item de pedido a ser excluído", required = true)
            @PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
