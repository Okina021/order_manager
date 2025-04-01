package com.example.project_orders_manager.controllers;

import com.example.project_orders_manager.domain.dto.addressDTOs.AddressDTO;
import com.example.project_orders_manager.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/address")
public class AddressController {
    @Autowired
    private AddressService service;

    @GetMapping
    public ResponseEntity<Page<AddressDTO>> getAddresses(Pageable pageable){
        return ResponseEntity.ok(service.listAddresses(pageable));
    }
    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(@PathVariable UUID id){
        return ResponseEntity.ok(service.getAddressById(id));
    }

    @Operation(summary = "Cadastrar novo Endereço", description = "Cria um novo Endereço e retorna os dados cadastrados")
    @PostMapping
    public ResponseEntity<AddressDTO> postCustomer(@RequestBody AddressDTO addressDTO) {
        AddressDTO addressToSave = service.save(addressDTO);
        URI location = URI.create("/api/v1/customers/" + addressDTO.id());
        return ResponseEntity.created(location).body(addressToSave);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> update(@PathVariable UUID id, @RequestBody AddressDTO addressDTO){
        return ResponseEntity.ok(service.update(id, addressDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable UUID id){
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
