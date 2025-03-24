package com.example.project_orders_manager.controllers;

import com.example.project_orders_manager.domain.dto.customerDTOs.CustomerDTO;
import com.example.project_orders_manager.domain.dto.customerDTOs.CustomerSummaryDTO;
import com.example.project_orders_manager.services.CustomerService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
@Tag(name = "Customer", description = "Endpoints para gerenciamento de clientes")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @Operation(summary = "Listar todos os cliente", description = "Retorna uma lista com todos os clientes cadastrados")
    @GetMapping()
    public ResponseEntity<List<CustomerSummaryDTO>> getCustomers() {
        return ResponseEntity.ok(service.getAllCustomers());
    }
    @Operation(summary = "Retorna Cliente pelo Id", description = "Retorna o cliente cadastrado peo id especificado")
    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable long id) {
        return ResponseEntity.ok(service.getCustomerById(id));
    }

    @Operation(summary = "Atualiza cliente pelo id informado", description = "Atualiza cliente pelo id informado")
    @PutMapping("/{id}")
    public ResponseEntity<CustomerDTO> putCustomer( @PathVariable Long id, @RequestBody CustomerDTO customerDTO) {
        return ResponseEntity.ok(service.updateCustomer(id, customerDTO));
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> postCustomer(@RequestBody CustomerDTO customerDTO) {
        CustomerDTO customerSave = service.save(customerDTO);
        URI location = URI.create("/api/v1/customers/" + customerSave.id());
        return ResponseEntity.created(location).body(customerSave);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomer(@PathVariable Long id) {
        service.deleteCustomer(id);
        return ResponseEntity.noContent().build();
    }
}
