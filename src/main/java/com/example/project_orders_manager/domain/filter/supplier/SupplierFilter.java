package com.example.project_orders_manager.domain.filter.supplier;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SupplierFilter {
    private String municipalRegistration;
    private String stateRegistration;
    private String cnpj;
    private Boolean active;
    private LocalDateTime dateFrom;
    private LocalDateTime dateTo;
}
