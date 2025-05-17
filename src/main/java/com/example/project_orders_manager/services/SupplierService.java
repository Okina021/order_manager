package com.example.project_orders_manager.services;

import com.example.project_orders_manager.domain.dto.addressDTOs.AddressDTO;
import com.example.project_orders_manager.domain.dto.suppliersDTOs.SuppliersDTO;
import com.example.project_orders_manager.domain.entities.Address;
import com.example.project_orders_manager.domain.entities.Supplier;
import com.example.project_orders_manager.domain.filter.supplier.SupplierFilter;
import com.example.project_orders_manager.domain.filter.supplier.SupplierSpecifications;
import com.example.project_orders_manager.repositories.AddressRepository;
import com.example.project_orders_manager.repositories.SupplierRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class SupplierService {

    @Autowired
    private SupplierRepository repository;

    @Autowired
    private AddressRepository addressRepository;


    public Page<SuppliersDTO> listSuppliers(SupplierFilter filter, Pageable pageable) {
        Specification<Supplier> specs = SupplierSpecifications.withFilters(filter);
        return repository.findAll(specs, pageable).map(SuppliersDTO::fromEntity);
    }



    public SuppliersDTO getSupplier(UUID id) {
        return SuppliersDTO.fromEntity(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Supplier not found")));
    }

    public SuppliersDTO getSupplierByCnpj(String cnpj) {
        return SuppliersDTO.fromEntity(repository.findByCnpj(cnpj).orElseThrow(() -> new EntityNotFoundException("Supplier not found")));
    }

    public SuppliersDTO saveSupplier(SuppliersDTO suppliersDTO) {
        Supplier supplier = SuppliersDTO.toEntity(suppliersDTO);

        AddressDTO addressDTO = suppliersDTO.address();
        if (addressDTO.id() != null) {
            Address existingAddress = addressRepository.findById(addressDTO.id())
                    .orElseThrow(() -> new EntityNotFoundException("Address not found"));
            supplier.setAddress(existingAddress);
        } else {
            supplier.setAddress(AddressDTO.toEntity(addressDTO));
        }

        return SuppliersDTO.fromEntity(repository.save(supplier));
    }


    public SuppliersDTO updateSupplier(UUID id, SuppliersDTO suppliersDTO) {
        Supplier supplier = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Supplier not found"));
        Optional.ofNullable(suppliersDTO.cnpj()).ifPresent(supplier::setCnpj);
        if (suppliersDTO.address() != null) {
            Address address = addressRepository.findById(suppliersDTO.address().id())
                    .orElseThrow(() -> new RuntimeException("Address not found"));
            supplier.setAddress(address);
        }
        Optional.ofNullable(suppliersDTO.active()).ifPresent(supplier::setActive);
        Optional.ofNullable(suppliersDTO.municipalRegistration()).ifPresent(supplier::setMunicipalRegistration);
        Optional.ofNullable(suppliersDTO.companyName()).ifPresent(supplier::setCompanyName);
        Optional.ofNullable(suppliersDTO.stateRegistration()).ifPresent(supplier::setStateRegistration);
        Optional.ofNullable(suppliersDTO.taxRegime()).ifPresent(supplier::setTaxRegime);
        Optional.ofNullable(suppliersDTO.tradingName()).ifPresent(supplier::setTradingName);
        return SuppliersDTO.fromEntity(repository.save(supplier));
    }


}
