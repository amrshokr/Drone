package com.app.drone.repository;

import java.math.BigDecimal;
import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.app.drone.model.Drone;

public interface DroneRepository extends CrudRepository<Drone, BigDecimal>{

	public List<Drone> findAll();
}
