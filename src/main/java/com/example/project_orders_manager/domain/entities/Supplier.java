package com.example.project_orders_manager.domain.entities;

import com.example.project_orders_manager.domain.enums.TaxRegime;
import jakarta.persistence.*;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.UUID;

@Data
@Entity
@Table(name = "suppliers")
public class Supplier extends BaseEntity implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    private String companyName;

    private String tradingName;

    @Size(min = 14, max = 14)
    @Column(name = "cnpj",unique = true)
    private String cnpj;

    private String stateRegistration;

    private String municipalRegistration;

    private Boolean active;

    @Enumerated(EnumType.STRING)
    private TaxRegime taxRegime;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "id")
    private Address address;
}

