package com.example.project_orders_manager.domain.dto.customerDTOs;

import com.example.project_orders_manager.domain.dto.addressDTOs.AddressDTO;
import com.example.project_orders_manager.domain.dto.addressDTOs.BillingAddressDTO;
import com.example.project_orders_manager.domain.dto.orderDTOs.OrderSummaryDTO;
import com.example.project_orders_manager.domain.entities.Customer;
import com.example.project_orders_manager.domain.entities.Order;

import java.util.List;
import java.util.UUID;

public record CustomerDTO(
        UUID id,
        String name,
        String surname,
        String doc,
        String email,
        BillingAddressDTO billing_address,
        List<AddressDTO> shipping_addresses,
        List<OrderSummaryDTO> orders) {

    public static CustomerDTO fromEntity(Customer customer) {
        return new CustomerDTO(
                customer.getId(),
                customer.getName(),
                customer.getSurname(),
                customer.getDoc(),
                customer.getEmail(),
                customer.getBillingAddress() != null ? BillingAddressDTO.fromEntity(customer.getBillingAddress()) : null,
                customer.getShippingAddresses() != null ?  customer.getShippingAddresses().stream().map(AddressDTO::fromEntity).toList() : null,
                customer.getOrders() != null ?  customer.getOrders().stream().map(OrderSummaryDTO::fromEntity).toList() : null
        );
    }

    public static Customer toEntity(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setName(customerDTO.name().toUpperCase());
        customer.setSurname(customerDTO.surname().toUpperCase());
        customer.setDoc(String.valueOf(customerDTO.doc()));
        customer.setEmail(customerDTO.email().toLowerCase());
        if(customerDTO.billing_address()!= null) {
            customer.setBillingAddress(BillingAddressDTO.toEntity(customerDTO.billing_address()));
        }
        if (customerDTO.shipping_addresses() != null) {
            customer.setShippingAddresses(
                    customerDTO.shipping_addresses().stream().map(AddressDTO::toEntity
                    ).toList()
            );
        }
        if (customerDTO.orders() != null) {
            customer.setOrders(
                    customerDTO.orders.stream().map(orderDTO -> {
                        Order order = new Order();
                        order.setId(orderDTO.id());
                        order.setStatus(orderDTO.status());
                        order.setCustomer(customer);
                        return order;
                    }).toList()
            );
        }
        return customer;
    }

    public static Customer toEntityWithId(CustomerDTO customerDTO) {
        Customer customer = new Customer();
        customer.setId(customerDTO.id());
        customer.setName(customerDTO.name().toUpperCase());
        customer.setSurname(customerDTO.surname().toUpperCase());
        customer.setDoc(String.valueOf(customerDTO.doc()));
        customer.setEmail(customerDTO.email().toLowerCase());
        if(customerDTO.billing_address()!= null) {
            customer.setBillingAddress(BillingAddressDTO.toEntity(customerDTO.billing_address()));
        }
        if (customerDTO.shipping_addresses() != null) {
            customer.setShippingAddresses(
                    customerDTO.shipping_addresses().stream().map(AddressDTO::toEntity
                    ).toList()
            );
        }
        if (customerDTO.orders() != null) {
            customer.setOrders(
                    customerDTO.orders.stream().map(orderDTO -> {
                        Order order = new Order();
                        order.setId(orderDTO.id());
                        order.setStatus(orderDTO.status());
                        order.setCustomer(customer);
                        return order;
                    }).toList()
            );
        }
        return customer;
    }
}
