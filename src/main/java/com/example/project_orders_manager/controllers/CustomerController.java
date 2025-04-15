package com.example.project_orders_manager.controllers;

import com.example.project_orders_manager.domain.dto.addressDTOs.AddressSummaryDTO;
import com.example.project_orders_manager.domain.dto.customerDTOs.CustomerDTO;
import com.example.project_orders_manager.domain.dto.customerDTOs.CustomerSummaryDTO;
import com.example.project_orders_manager.services.CustomerService;
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
@RequestMapping("/api/v1/customers")
@Tag(name = "Customer", description = "Endpoints para gerenciamento de clientes")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @Operation(summary = "Listar endereços", description = "Retorna uma lista paginada com todos os endereços cadastrados. É possível filtrar por data inicial e final.")
    @ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<CustomerSummaryDTO>> listOrders(
            @Parameter(description = "Data inicial para filtro (yyyy-MM-dd)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime dateFrom,

            @Parameter(description = "Data final para filtro (yyyy-MM-dd)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime dateTo,

            @Parameter(hidden = true)
            Pageable pageable) {
        return ResponseEntity.ok(service.listCustomers(dateFrom, dateTo, pageable));
    }

    @Operation(summary = "Buscar cliente por ID", description = "Retorna os detalhes de um cliente cadastrado pelo ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = @Content(schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(
            @Parameter(description = "UUID do cliente", required = true)
            @PathVariable UUID id) {
        return ResponseEntity.ok(service.getCustomerById(id));
    }

    @Operation(summary = "Buscar cliente por documento", description = "Retorna os dados de um cliente com base no CPF ou CNPJ")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente encontrado",
                    content = @Content(schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @GetMapping(params = "doc")
    public ResponseEntity<CustomerDTO> getCustomerByDocument(
            @Parameter(description = "CPF ou CNPJ do cliente", required = true)
            @RequestParam String doc) {
        return ResponseEntity.ok(service.getCustomerByDoc(doc));
    }

    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente com base no ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Cliente atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> updateCustomer(
            @Parameter(description = "UUID do cliente a ser atualizado", required = true)
            @PathVariable UUID id,

            @Parameter(description = "Dados atualizados do cliente", required = true)
            @RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok(service.updateCustomer(id, customerDTO));
    }

    @Operation(summary = "Cadastrar novo cliente", description = "Cria um novo cliente e retorna os dados cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Cliente criado com sucesso",
                    content = @Content(schema = @Schema(implementation = CustomerDTO.class))),
            @ApiResponse(responseCode = "400", description = "Dados inválidos")
    })
    @PostMapping
    public ResponseEntity<CustomerDTO> createCustomer(
            @Parameter(description = "Dados do novo cliente", required = true)
            @Valid @RequestBody CustomerDTO customerDTO) {
        CustomerDTO customerSave = service.save(customerDTO);
        URI location = URI.create("/api/v1/customers/" + customerSave.id());
        return ResponseEntity.created(location).body(customerSave);
    }

    @Operation(summary = "Excluir cliente", description = "Remove um cliente cadastrado com base no ID informado")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Cliente removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Cliente não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(
            @Parameter(description = "UUID do cliente a ser excluído", required = true)
            @PathVariable UUID id) {
        service.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
