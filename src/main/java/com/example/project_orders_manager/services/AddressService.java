package com.example.project_orders_manager.services;

import com.example.project_orders_manager.domain.dto.addressDTOs.AddressDTO;
import com.example.project_orders_manager.domain.dto.addressDTOs.AddressSummaryDTO;
import com.example.project_orders_manager.domain.entities.Address;
import com.example.project_orders_manager.domain.entities.Customer;
import com.example.project_orders_manager.repositories.AddressRepository;
import com.example.project_orders_manager.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {
    @Autowired
    private AddressRepository repository;
    @Autowired
    private CustomerRepository customerRepository;

    public Page<AddressSummaryDTO> listAddress(LocalDateTime dateFrom, LocalDateTime dateTo, Pageable pageable) {
        if (dateTo == null) {
            dateTo = LocalDateTime.now();
        }
        if (dateFrom == null) {
            return repository.findByDateBefore(dateTo, pageable).map(AddressSummaryDTO::fromEntity);
        }
        return repository.findByDateBetween(dateFrom, dateTo, pageable).map(AddressSummaryDTO::fromEntity);
    }

    public AddressDTO getAddressById(UUID id) {
        return AddressDTO.fromEntity(repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Address not found")));
    }

    public AddressDTO save(AddressDTO addressDTO) {
        AddressDTO toSave = AddressDTO.fromEntity(repository.save(AddressDTO.toEntity(addressDTO)));
        if (addressDTO.principalAddress()) {
            Customer customer = customerRepository.findById(addressDTO.customerId()).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
            customer.setBillingAddress(repository.findById(toSave.id()).orElseThrow(() -> new EntityNotFoundException("Address not found")));
            customerRepository.save(customer);
        }
        return toSave;
    }

    public AddressDTO update(UUID id, AddressDTO addressDTO) {
        Address toSave = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Address not found id: " + id));
        Optional.ofNullable(addressDTO.street()).ifPresent(toSave::setStreet);
        Optional.ofNullable(addressDTO.number()).ifPresent(toSave::setNumber);
        Optional.ofNullable(addressDTO.complement()).ifPresent(toSave::setComplement);
        Optional.ofNullable(addressDTO.neighborhood()).ifPresent(toSave::setNeighborhood);
        Optional.ofNullable(addressDTO.city()).ifPresent(toSave::setCity);
        Optional.ofNullable(addressDTO.state()).ifPresent(toSave::setState);
        Optional.ofNullable(addressDTO.country()).ifPresent(toSave::setCountry);
        Optional.ofNullable(addressDTO.postalCode()).ifPresent(toSave::setPostalCode);
        Optional.ofNullable(addressDTO.principalAddress()).ifPresent(toSave::setPrincipalAddress);
        if (toSave.getPrincipalAddress()) {
            Customer customer = customerRepository.findById(toSave.getCustomer().getId()).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
            customer.setBillingAddress(repository.findById(toSave.getId()).orElseThrow(() -> new EntityNotFoundException("Address not found")));
            customerRepository.save(customer);
        }
        return AddressDTO.fromEntity(repository.save(toSave));
    }

    public void delete(UUID id) {
        Address address = repository.findById(id).orElseThrow(() -> new EntityNotFoundException("Address not found by id: " + id));
        if (address.getPrincipalAddress()) {
            Customer customer = customerRepository.findById(address.getCustomer().getId()).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
            customer.setBillingAddress(null);
            customerRepository.save(customer);
            System.err.println("Billing address of customer_id: " + address.getCustomer().getId() + " was been excluded");
        }
        repository.deleteById(id);
    }

}
