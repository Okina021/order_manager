package com.example.project_orders_manager.controllers;

import com.example.project_orders_manager.domain.dto.orderItemDTOs.OrderItemDTO;
import com.example.project_orders_manager.domain.dto.suppliersDTOs.SuppliersDTO;
import com.example.project_orders_manager.domain.filter.supplier.SupplierFilter;
import com.example.project_orders_manager.services.SupplierService;
import io.swagger.v3.oas.annotations.Parameter;
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
@RequestMapping("/api/v1/suppliers")
public class SupplierController {

    @Autowired
    private SupplierService service;

    @GetMapping
    public Page<SuppliersDTO> listSuppliers(
            @RequestParam(required = false) String municipalRegistration,
            @RequestParam(required = false) String StateRegistration,
            @RequestParam(required = false) String cnpj,
            @RequestParam(required = false) Boolean active,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateFrom,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) LocalDateTime dateTo,
            Pageable pageable
    ) {
        SupplierFilter filter = new SupplierFilter();
        filter.setMunicipalRegistration(municipalRegistration);
        filter.setStateRegistration(StateRegistration);
        filter.setCnpj(cnpj);
        filter.setActive(active);
        filter.setDateFrom(dateFrom);
        filter.setDateTo(dateTo);
        return service.listSuppliers(filter, pageable);
    }


    @GetMapping("/{id}")
    public ResponseEntity<SuppliersDTO> getSupplier(@PathVariable UUID id){
        return ResponseEntity.ok(service.getSupplier(id));
    }

    @GetMapping(params = "cnpj")
    public ResponseEntity<SuppliersDTO> getSupplier(@RequestParam String cnpj){
        return ResponseEntity.ok(service.getSupplierByCnpj(cnpj));
    }

    @PostMapping
    public ResponseEntity<SuppliersDTO> postOrderItem(
            @Parameter(description = "Dados do novo fornecedor", required = true)
            @Valid @RequestBody SuppliersDTO suppliersDTO) {
        return ResponseEntity.ok(service.saveSupplier(suppliersDTO));
    }


}
