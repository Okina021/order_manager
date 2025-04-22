package com.example.project_orders_manager.controllers;

import com.example.project_orders_manager.domain.dto.addressDTOs.AddressDTO;
import com.example.project_orders_manager.domain.dto.addressDTOs.AddressSummaryDTO;
import com.example.project_orders_manager.services.AddressService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
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
@RequestMapping("/api/v1/address")
public class AddressController {

    @Autowired
    private AddressService service;

    @Operation(summary = "Listar endereços", description = "Retorna uma lista paginada com todos os endereços cadastrados. É possível filtrar por data inicial e final.")
    @ApiResponse(responseCode = "200", description = "Lista de endereços retornada com sucesso")
    @GetMapping
    public ResponseEntity<Page<AddressSummaryDTO>> listOrders(
            @Parameter(description = "Data inicial para filtro (yyyy-MM-dd)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime dateFrom,

            @Parameter(description = "Data final para filtro (yyyy-MM-dd)")
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDateTime dateTo,

            @Parameter(hidden = true)
            Pageable pageable) {
        return ResponseEntity.ok(service.listAddress(dateFrom, dateTo, pageable));
    }

    @Operation(summary = "Buscar endereço por ID", description = "Retorna os detalhes de um endereço a partir do seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço encontrado com sucesso",
                    content = @Content(schema = @Schema(implementation = AddressDTO.class))),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    @GetMapping("/{id}")
    public ResponseEntity<AddressDTO> getAddressById(
            @Parameter(description = "ID do endereço a ser buscado") @PathVariable UUID id) {
        return ResponseEntity.ok(service.getAddressById(id));
    }

    @Operation(summary = "Cadastrar novo endereço", description = "Cria um novo endereço e retorna os dados cadastrados")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Endereço criado com sucesso",
                    content = @Content(schema = @Schema(implementation = AddressDTO.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida")
    })
    @PostMapping
    public ResponseEntity<AddressDTO> createAddress(@Valid @RequestBody AddressDTO addressDTO) {
        AddressDTO addressToSave = service.save(addressDTO);
        URI location = URI.create("/api/v1/address/" + addressToSave.id());
        return ResponseEntity.created(location).body(addressToSave);
    }

    @Operation(summary = "Atualizar endereço", description = "Atualiza os dados de um endereço existente")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Endereço atualizado com sucesso",
                    content = @Content(schema = @Schema(implementation = AddressDTO.class))),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    @PutMapping("/{id}")
    public ResponseEntity<AddressDTO> updateAddress(
            @Parameter(description = "ID do endereço a ser atualizado") @PathVariable UUID id,
            @RequestBody AddressDTO addressDTO) {
        return ResponseEntity.ok(service.update(id, addressDTO));
    }

    @Operation(summary = "Deletar endereço", description = "Remove um endereço a partir do seu ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "Endereço removido com sucesso"),
            @ApiResponse(responseCode = "404", description = "Endereço não encontrado")
    })
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(
            @Parameter(description = "ID do endereço a ser deletado") @PathVariable UUID id) {
        service.delete(id);
        return ResponseEntity.noContent().build();
    }
}
