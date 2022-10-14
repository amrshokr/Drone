package com.app.drone.controller;


import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.app.drone.model.Drone;
import com.app.drone.model.DroneCreationRequest;
import com.app.drone.model.DroneCreationResponse;
import com.app.drone.model.MedicationsCreationResponse;
import com.app.drone.model.MidicationCreationReqList;
import com.app.drone.repository.DroneRepository;
import com.app.drone.repository.MedicationRepository;
import com.app.drone.service.DroneService;

@RestController
public class DroneController {
	
	private static final Logger log = LoggerFactory.getLogger(DroneController.class);
	
	@Autowired
	DroneService droneService;
	
	@Autowired
	DroneRepository droneRepository;
	
	@Autowired 
	MedicationRepository medicationRepository;
	
	@GetMapping("/drone/getdrones")
	public ResponseEntity<List<Drone>> getAllEmployees(){
		log.info("into getAllEmployee method");
		List<Drone> drones=droneService.getAllDrones();
		return new ResponseEntity<List<Drone>>(drones,HttpStatus.OK);
	}
	
	@PostMapping("/drone/save-drone")
	public ResponseEntity<DroneCreationResponse> saveDrone(DroneCreationRequest request){
		log.info("into saveDrone method");
		DroneCreationResponse createdDrone=droneService.createDrone(request);
		return new ResponseEntity<DroneCreationResponse>(createdDrone,HttpStatus.OK);
	}
	
	@PostMapping("/drone/save-medication")
	  public ResponseEntity<MedicationsCreationResponse> saveMedications(@RequestBody MidicationCreationReqList request) {
	    String message = "";
	    try {
	    	
	    	droneService.store(request);

	      message = "saved successfully " ;
	      return ResponseEntity.status(HttpStatus.OK).body(new MedicationsCreationResponse(message));
	    } catch (Exception e) {
	      message = "Could not save the data: " +request + "!";
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MedicationsCreationResponse(message));
	    }
	  }
	

}
