package com.app.drone.service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import javax.sql.rowset.serial.SerialException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.app.drone.manager.DroneManager;
import com.app.drone.model.Drone;
import com.app.drone.model.DroneCreationRequest;
import com.app.drone.model.DroneCreationResponse;
import com.app.drone.model.MedicationsCreationRequest;
import com.app.drone.model.MedicationsCreationResponse;
import com.app.drone.model.MidicationCreationReqList;
import com.app.drone.repository.DroneRepository;
import com.app.drone.repository.MedicationRepository;


@Component
public class DroneService {
	private static final Logger log = LoggerFactory.getLogger(DroneService.class);
	
	@Autowired
	DroneRepository droneRepository;
	@Autowired 
	MedicationRepository medicationRepository;
	
	
	@Autowired
	DroneManager droneManager; 
	
	public List<Drone> getAllDrones(){
		
		List<Drone> drones=droneRepository.findAll();
		
		return drones;
	}
	
	public DroneCreationResponse createDrone(DroneCreationRequest request) {
		DroneCreationResponse drone=new DroneCreationResponse();
		drone=droneManager.saveDrone(request);
		return drone;
	}
	
	public MedicationsCreationResponse store(MedicationsCreationRequest request) throws IOException, SerialException, SQLException {
		MedicationsCreationResponse response=droneManager.saveMedication(request);
	    
	    return response;
	  }

}
