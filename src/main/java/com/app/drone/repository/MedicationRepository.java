package com.app.drone.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.app.drone.model.Medication;

public interface MedicationRepository extends CrudRepository<Medication, BigDecimal> {
	
	@Query(value = "SELECT * FROM MEDICATION WHERE DRONE_ID is null",nativeQuery = true)
	public List<Medication> getNotLoadedMedicationList();
	
	@Query(value = "SELECT * FROM MEDICATION WHERE MEDICATION_CODE =?1",nativeQuery = true)
	public List<Medication> getByCode(String code);
	
	@Query(value = "SELECT * FROM MEDICATION WHERE MEDICATION_NAME =?1",nativeQuery = true)
	public List<Medication> getByName(String name);

}
