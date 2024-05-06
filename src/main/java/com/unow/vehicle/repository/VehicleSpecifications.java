package com.unow.vehicle.repository;

import com.unow.vehicle.model.Vehicle;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

public class VehicleSpecifications {
    public static Specification<Vehicle> withDynamicQuery(final String brand, final String model, final String licencePlate) {
        return (root, query, criteriaBuilder) -> {
            List<Predicate> predicates = new ArrayList<>();

            if (brand != null && !brand.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("brand"), brand));
            }
            if (model != null && !model.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("model"), model));
            }
            if (licencePlate != null && !licencePlate.trim().isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("licencePlate"), licencePlate));
            }

            return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
        };
    }
}