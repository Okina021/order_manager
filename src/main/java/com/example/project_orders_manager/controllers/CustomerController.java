package com.example.project_orders_manager.controllers;

import com.example.project_orders_manager.domain.dto.customerDTOs.CustomerDTO;
import com.example.project_orders_manager.domain.dto.customerDTOs.CustomerSummaryDTO;
import com.example.project_orders_manager.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customer", description = "Endpoints para gerenciamento de clientes")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @Operation(summary = "Listar todos os clientes", description = "Retorna uma lista paginada com todos os clientes cadastrados")
    @GetMapping
    public ResponseEntity<Page<CustomerSummaryDTO>> getCustomers(Pageable pageable) {
        return ResponseEntity.ok(service.getAllCustomers(pageable));
    }

    @Operation(summary = "Buscar cliente por ID", description = "Retorna os detalhes de um cliente cadastrado pelo ID informado")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable UUID id) {
        return ResponseEntity.ok(service.getCustomerById(id));
    }

    @GetMapping(params = "doc")
    public ResponseEntity<CustomerDTO> getCustomerByDoc(@RequestParam String doc) {
        return ResponseEntity.ok(service.getCustomerByDoc(doc));
    }

    @Operation(summary = "Atualizar cliente", description = "Atualiza os dados de um cliente com base no ID informado")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> putCustomer(@PathVariable UUID id, @RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok(service.updateCustomer(id, customerDTO));
    }

    @Operation(summary = "Cadastrar novo cliente", description = "Cria um novo cliente e retorna os dados cadastrados")
    @PostMapping
    public ResponseEntity<CustomerDTO> postCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO customerSave = service.save(customerDTO);
        URI location = URI.create("/api/v1/customers/" + customerSave.id());
        return ResponseEntity.created(location).body(customerSave);
    }

    @Operation(summary = "Excluir cliente", description = "Remove um cliente cadastrado com base no ID informado")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable UUID id) {
        service.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
