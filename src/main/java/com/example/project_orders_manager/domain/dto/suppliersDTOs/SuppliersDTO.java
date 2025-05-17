package com.example.project_orders_manager.domain.dto.suppliersDTOs;

import com.example.project_orders_manager.domain.dto.addressDTOs.AddressDTO;
import com.example.project_orders_manager.domain.entities.Address;
import com.example.project_orders_manager.domain.entities.Supplier;
import com.example.project_orders_manager.domain.enums.TaxRegime;
import com.example.project_orders_manager.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.UUID;

public record SuppliersDTO(
        UUID id,
        String companyName,
        String tradingName,
        String cnpj,
        String stateRegistration,
        String municipalRegistration,
        Boolean active,
        TaxRegime taxRegime,
        AddressDTO address
) {


    public static Supplier toEntity(SuppliersDTO suppliersDTO) {
        Supplier supplier = new Supplier();
        supplier.setId(suppliersDTO.id());
        supplier.setCompanyName(suppliersDTO.companyName());
        supplier.setTradingName(suppliersDTO.tradingName());
        supplier.setCnpj(suppliersDTO.cnpj());
        supplier.setStateRegistration(suppliersDTO.stateRegistration());
        supplier.setMunicipalRegistration(suppliersDTO.municipalRegistration());
        supplier.setActive(suppliersDTO.active());
        supplier.setTaxRegime(suppliersDTO.taxRegime());
        supplier.setAddress(AddressDTO.toEntity(suppliersDTO.address()));
        return supplier;
    }

    public static SuppliersDTO fromEntity(Supplier supplier) {
        return new SuppliersDTO(supplier.getId(),
                supplier.getCompanyName(),
                supplier.getTradingName(),
                supplier.getCnpj(),
                supplier.getStateRegistration(),
                supplier.getMunicipalRegistration(),
                supplier.getActive(),
                supplier.getTaxRegime(),
                AddressDTO.fromEntity(supplier.getAddress()));
    }
}
