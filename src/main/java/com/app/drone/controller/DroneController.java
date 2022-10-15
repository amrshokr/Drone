package com.app.drone.controller;


import java.math.BigDecimal;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.app.drone.model.Drone;
import com.app.drone.model.DroneCreationRequest;
import com.app.drone.model.DroneCreationResponse;
import com.app.drone.model.MedicationsCreationRequest;
import com.app.drone.model.MedicationsCreationResponse;
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
	
	@GetMapping("/drone/getdrones/")
	public ResponseEntity<List<Drone>> getAllDrones(){
		log.info("into getAllEmployee method");
		List<Drone> drones=droneService.getAllDrones();
		return new ResponseEntity<List<Drone>>(drones,HttpStatus.OK);
	}
	
	@PostMapping("/drone/save-drone/")
	public ResponseEntity<DroneCreationResponse> saveDrone(@RequestBody DroneCreationRequest request){
		log.info("into saveDrone method");
		DroneCreationResponse createdDrone=droneService.createDrone(request);
		return new ResponseEntity<DroneCreationResponse>(createdDrone,HttpStatus.OK);
	}
	
	@PostMapping("/drone/save-medication/")
	  public ResponseEntity<MedicationsCreationResponse> saveMedications(@RequestParam MultipartFile file,@RequestParam String medname ,@RequestParam BigDecimal weight ,@RequestParam String code) {
	    String message = "";
	    MedicationsCreationRequest request=new MedicationsCreationRequest();
	    try {
	    	
	    	request.setCode(code);
	    	request.setFile(file);
	    	request.setName(medname);
	    	request.setWeight(weight);
	    	
	    	droneService.store(request);

	      message = "saved successfully " ;
	      return ResponseEntity.status(HttpStatus.OK).body(new MedicationsCreationResponse(message));
	    } catch (Exception e) {
	      message = "Could not save the data: " +e.getMessage() + "!";
	      log.error("Error While Save Medication "+e.getMessage());
	      return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new MedicationsCreationResponse(message));
	    }
	  }
	
	
	

}
