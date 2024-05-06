package com.unow.vehicle.repository;

import com.unow.vehicle.model.Vehicle;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.criteria.Predicate;
import org.springframework.data.jpa.domain.Specification;

/**
 * The Vehicle specifications class.
 */
public class VehicleSpecifications {
  /**
   * Creates a dynamic query for the received parameters.
   *
   * @param brand the Vehicle brand
   * @param model the Vehicle model
   * @param licensePlate the Vehicle license plate
   * @return the specification
   */
  public static Specification<Vehicle> withDynamicQuery(
      final String brand, final String model, final String licensePlate) {
    return (root, query, criteriaBuilder) -> {
      List<Predicate> predicates = new ArrayList<>();

      if (brand != null && !brand.trim().isEmpty()) {
        predicates.add(criteriaBuilder.equal(root.get("brand"), brand));
      }
      if (model != null && !model.trim().isEmpty()) {
        predicates.add(criteriaBuilder.equal(root.get("model"), model));
      }
      if (licensePlate != null && !licensePlate.trim().isEmpty()) {
        predicates.add(criteriaBuilder.equal(root.get("licensePlate"), licensePlate));
      }

      return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
