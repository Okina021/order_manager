package com.example.project_orders_manager.services;

import com.example.project_orders_manager.domain.dto.addressDTOs.AddressDTO;
import com.example.project_orders_manager.domain.entities.Address;
import com.example.project_orders_manager.domain.entities.Customer;
import com.example.project_orders_manager.repositories.AddressRepository;
import com.example.project_orders_manager.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class AddressService {
    @Autowired
    private AddressRepository repository;
    @Autowired
    private CustomerRepository customerRepository;

    public Page<AddressDTO> listAddresses(Pageable pageable){
        return repository.findAll(pageable).map(AddressDTO::fromEntity);
    }

    public AddressDTO getAddressById(UUID id){
        return AddressDTO.fromEntity(repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Address not found")));
    }

    public AddressDTO save(AddressDTO addressDTO){
        AddressDTO toSave = AddressDTO.fromEntity(repository.save(AddressDTO.toEntity(addressDTO)));
        if (addressDTO.principal_address() && addressDTO.customer_id() != null){
            Customer customer = customerRepository.findById(addressDTO.customer_id()).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
            customer.setBillingAddress(repository.findById(toSave.id()).orElseThrow(()-> new EntityNotFoundException("Address not found")));
            customerRepository.save(customer);
        }
        return toSave;
    }

    public AddressDTO update(UUID id,AddressDTO addressDTO){
        Address toSave = repository.findById(id).orElseThrow(()-> new EntityNotFoundException("Address not found id: " + id));
        Optional.ofNullable(addressDTO.street()).ifPresent(toSave::setStreet);
        Optional.ofNullable(addressDTO.number()).ifPresent(toSave::setNumber);
        Optional.ofNullable(addressDTO.complement()).ifPresent(toSave::setComplement);
        Optional.ofNullable(addressDTO.neighborhood()).ifPresent(toSave::setNeighborhood);
        Optional.ofNullable(addressDTO.city()).ifPresent(toSave::setCity);
        Optional.ofNullable(addressDTO.state()).ifPresent(toSave::setState);
        Optional.ofNullable(addressDTO.country()).ifPresent(toSave::setCountry);
        Optional.ofNullable(addressDTO.postal_code()).ifPresent(toSave::setPostalCode);
        return AddressDTO.fromEntity(repository.save(toSave));
    }

    public void delete(UUID id){
        repository.findById(id).orElseThrow(()->new EntityNotFoundException("Address not found by id: "+ id));
        repository.deleteById(id);
    }

}
