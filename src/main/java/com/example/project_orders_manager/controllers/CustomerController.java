package com.example.project_orders_manager.controllers;

import com.example.project_orders_manager.models.Customer;
import com.example.project_orders_manager.models.dto.CustomerDTO;
import com.example.project_orders_manager.services.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api/v1/customers")
public class CustomerController {

    @Autowired
    private CustomerService service;

    @GetMapping()
    public ResponseEntity<List<CustomerDTO>> getCustomers() {
        return ResponseEntity.ok(service.getAllCustomers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerDTO> getCustomerById(@PathVariable long id){
        try{
            return ResponseEntity.ok(service.getCustomerById(id));
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping
    public ResponseEntity<CustomerDTO> postCustomer(@RequestBody CustomerDTO customer) {
        Customer customerSave = service.save(customer);
        CustomerDTO customerDTO = CustomerDTO.fromEntity(customerSave);
        URI location = URI.create("/api/v1/customers/" + customerSave.getId());
        return ResponseEntity.created(location).body(customerDTO);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteCustomer(@PathVariable Long id){
        try {
            service.deleteCustomer(id);
            return ResponseEntity.noContent().build();
        }catch (RuntimeException e){
            return ResponseEntity.notFound().build();
        }catch (Exception e){
            return ResponseEntity.internalServerError().build();
        }
    }
}
