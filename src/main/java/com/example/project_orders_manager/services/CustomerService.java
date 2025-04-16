package com.example.project_orders_manager.services;

import com.example.project_orders_manager.domain.dto.addressDTOs.AddressSummaryDTO;
import com.example.project_orders_manager.domain.dto.addressDTOs.BillingAddressDTO;
import com.example.project_orders_manager.domain.dto.customerDTOs.CustomerDTO;
import com.example.project_orders_manager.domain.dto.customerDTOs.CustomerSummaryDTO;
import com.example.project_orders_manager.domain.entities.Customer;
import com.example.project_orders_manager.exceptions.BadRequestException;
import com.example.project_orders_manager.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public Page<CustomerSummaryDTO> listCustomers(LocalDateTime dateFrom, LocalDateTime dateTo, Pageable pageable) {
        if (dateTo == null) {
            dateTo = LocalDateTime.now();
        }
        if (dateFrom == null) {
            return customerRepository.findByDateBefore(dateTo, pageable).map(CustomerSummaryDTO::fromEntity);
        }
        return customerRepository.findByDateBetween(dateFrom, dateTo, pageable).map(CustomerSummaryDTO::fromEntity);
    }

    public CustomerDTO getCustomerById(UUID id) {
        return customerRepository.findById(id).map(CustomerDTO::fromEntity).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    public CustomerDTO getCustomerByDoc(String doc) {
        return CustomerDTO.fromEntity(customerRepository.findByDoc(doc).orElseThrow(() -> new EntityNotFoundException("Doc not found")));
    }

    @Transactional
    public CustomerDTO save(CustomerDTO customer) {
        if (customer.id() != null) throw new BadRequestException("Customer id must be null");
        return CustomerDTO.fromEntity(customerRepository.save(CustomerDTO.toEntity(customer)));

    }

    @Transactional
    public CustomerDTO updateCustomer(UUID id, CustomerDTO customer) {
        Customer c = customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Optional.ofNullable(customer.name()).ifPresent(c::setName);
        Optional.ofNullable(customer.surname()).ifPresent(c::setSurname);
        Optional.ofNullable(customer.doc()).ifPresent(c::setDoc);
        Optional.ofNullable(customer.email()).ifPresent(c::setEmail);
        Optional.ofNullable(customer.billingAddress())
                .map(BillingAddressDTO::toEntity)
                .ifPresent(c::setBillingAddress);
        customerRepository.save(c);
        return CustomerDTO.fromEntity(c);
    }


    public void deleteCustomer(UUID id) {
        if (!customerRepository.existsById(id)) throw new EntityNotFoundException("Customer not found");
        customerRepository.deleteById(id);
    }
}
