package com.app.drone.repository;

import java.math.BigDecimal;

import org.springframework.data.repository.CrudRepository;

import com.app.drone.model.Medication;

public interface MedicationRepository extends CrudRepository<Medication, BigDecimal> {

}
