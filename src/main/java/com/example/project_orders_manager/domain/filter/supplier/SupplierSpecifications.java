package com.example.project_orders_manager.domain.filter.supplier;

import com.example.project_orders_manager.domain.entities.Supplier;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;

public class SupplierSpecifications {

    public static Specification<Supplier> withFilters(SupplierFilter filter) {
        return (root, query, cb) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (filter.getCnpj() != null) {
                predicates.add(cb.equal(root.get("cnpj"), filter.getCnpj()));
            }
            if (filter.getActive() != null) {
                predicates.add(cb.equal(root.get("active"), filter.getActive()));
            }
            if (filter.getMunicipalRegistration() != null) {
                predicates.add(cb.equal(root.get("address").get("city"), filter.getMunicipalRegistration()));
            }
            if (filter.getStateRegistration() != null) {
                predicates.add(cb.equal(root.get("address").get("state"), filter.getStateRegistration()));
            }
            if (filter.getDateFrom() != null && filter.getDateTo() != null) {
                predicates.add(cb.between(root.get("updatedAt"), filter.getDateFrom(), filter.getDateTo()));
            } else if (filter.getDateTo() != null) {
                predicates.add(cb.lessThanOrEqualTo(root.get("updatedAt"), filter.getDateTo()));
            }

            return cb.and(predicates.toArray(new Predicate[0]));
        };
    }
}
