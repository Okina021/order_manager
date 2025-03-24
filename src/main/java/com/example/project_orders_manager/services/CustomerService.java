package com.example.project_orders_manager.services;

import com.example.project_orders_manager.exceptions.BadRequestException;
import com.example.project_orders_manager.domain.Customer;
import com.example.project_orders_manager.domain.dto.customerDTOs.CustomerDTO;
import com.example.project_orders_manager.domain.dto.customerDTOs.CustomerSummaryDTO;
import com.example.project_orders_manager.repositories.CustomerRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerSummaryDTO> getAllCustomers() {
        return customerRepository.findAll()
                .stream()
                .map(CustomerSummaryDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(Long id) {
        return customerRepository.findById(id).map(CustomerDTO::fromEntity).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
    }

    public CustomerDTO save(CustomerDTO customer) {
        if (customer.id() != null) throw new BadRequestException("Customer id must be null");
        return CustomerDTO.fromEntity(customerRepository.save(CustomerDTO.toEntity(customer)));

    }

    public CustomerDTO updateCustomer(Long id, CustomerDTO customer) {
        Customer c = customerRepository.findById(id).orElseThrow(EntityNotFoundException::new);
        Optional.ofNullable(customer.name()).ifPresent(c::setName);
        Optional.ofNullable(customer.surname()).ifPresent(c::setSurname);
        Optional.ofNullable(customer.doc()).ifPresent(c::setDoc);
        return CustomerDTO.fromEntity(customerRepository.save(CustomerDTO.toEntity(customer)));

    }

    public void deleteCustomer(Long id) {
        if (!customerRepository.existsById(id)) throw new EntityNotFoundException("Customer not found");
        customerRepository.deleteById(id);
    }
}
