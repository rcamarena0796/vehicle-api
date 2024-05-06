package com.unow.vehicle.repository;

import com.unow.vehicle.model.Vehicle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

/** The interface Vehicle repository. */
@Repository
public interface VehicleRepository
    extends JpaRepository<Vehicle, Long>, JpaSpecificationExecutor<Vehicle> {}
