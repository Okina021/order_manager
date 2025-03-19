package com.example.project_orders_manager.services;

import com.example.project_orders_manager.models.Customer;
import com.example.project_orders_manager.models.dto.CustomerDTO;
import com.example.project_orders_manager.models.dto.OrderDTO;
import com.example.project_orders_manager.repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<CustomerDTO> getAllCustomers() {
        List<Customer> customers = customerRepository.findAll();
        return customers
                .stream()
                .map(CustomerDTO::fromEntity)
                .collect(Collectors.toList());
    }

    public CustomerDTO getCustomerById(Long id) {
        Optional<Customer> customer = customerRepository.findById(id);
        if (customer.isPresent()){
            return CustomerDTO.fromEntity(customer.get());
        }else {
            throw new RuntimeException("Customer not found");
        }
    }

    public Customer save(CustomerDTO customer) {
        Customer c = new Customer();
        c.setName(customer.name());
        c.setSurname(customer.surname());
        c.setDoc(customer.doc());
        return customerRepository.save(c);
    }

    public Customer updateCustomer(Long id, CustomerDTO customer) {
        Optional<CustomerDTO> c = Optional.of(this.getCustomerById(id));
        return this.save(customer);
    }

    public void deleteCustomer(Long id) {
        Optional<Customer> c = customerRepository.findById(id);
        if (c.isPresent()) {
            customerRepository.delete(c.get());
        } else {
            throw new RuntimeException("Id not found");
        }
    }
}
