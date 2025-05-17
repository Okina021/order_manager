package com.example.project_orders_manager;

import com.example.project_orders_manager.domain.dto.customerDTOs.CustomerDTO;
import com.example.project_orders_manager.services.CustomerService;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.UUID;

@SpringBootApplication
public class ProjectOrdersManagerApplication {

    public static void main(String[] args) {
       var context = SpringApplication.run(ProjectOrdersManagerApplication.class, args);

//       CustomerService service = context.getBean(CustomerService.class);
//
//        CustomerDTO customer = service.getCustomerById(UUID.fromString("e46dc548-edaa-4515-979a-70a39c691c6d"));
//
//        System.out.println(customer);
    }

}
