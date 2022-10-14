package com.app.drone.manager;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.Date;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.app.drone.Exceptions.DroneException;
import com.app.drone.model.Drone;
import com.app.drone.model.DroneCreationRequest;
import com.app.drone.model.Medication;
import com.app.drone.model.MedicationsCreationRequest;
import com.app.drone.model.MedicationsCreationResponse;
import com.app.drone.model.MidicationCreationReqList;
import com.app.drone.repository.DroneRepository;
import com.app.drone.repository.MedicationRepository;
import com.app.drone.util.Constants;

@Component
public class DroneManager {

	private static final Logger log = LoggerFactory.getLogger(DroneManager.class);

	@Autowired
	DroneRepository droneRepository;

	@Autowired
	MedicationRepository medicationRepository;

	public void saveDrone(DroneCreationRequest request) {
		if (request != null) {
			validateDroneSaveRequest(request);
			Drone drone = new Drone();

			drone.setSerialNumber(request.getSerialNumber());
			drone.setState(request.getState().getName());
			drone.setBatteryCapacity(request.getBatteryCapacity());
			drone.setCreatedDate(new Date());
			drone.setModel(request.getModel().getName());
			drone.setWeightLimit(request.getWeightLimit());

			droneRepository.save(drone);
			log.info("Drone Data Saved Successfully :" + drone);

		} else {
			new DroneException("No Record Found");
		}

	}

	public void validateDroneSaveRequest(DroneCreationRequest request) {
		if (request != null) {

			if (request.getSerialNumber() != null) {
				if (request.getSerialNumber().length() > 100) {
					new DroneException("Serial Number max characters is 100");
				}
			} else {
				new DroneException("Please enter a valid percentage for Battry Capacity");
			}
			if (request.getBatteryCapacity() != null) {
				if (request.getBatteryCapacity().compareTo(BigDecimal.ZERO) > 0
						&& request.getBatteryCapacity().compareTo(new BigDecimal(100)) < 0) {

				} else {
					new DroneException("Please enter a valid percentage for Battry Capacity");
				}
			}
			if (request.getWeightLimit() != null) {
				if (request.getWeightLimit().compareTo(Constants.WIGHT_LIMIT) < 0
						&& request.getWeightLimit().compareTo(BigDecimal.ZERO) > 0) {

				} else {
					new DroneException("Please enter a valid wieght limit 500 max");
				}
			}

		} else {
			new DroneException("No Record Found To Be Saved In Drone Table");
		}
	}

	public MedicationsCreationResponse saveMedication(MidicationCreationReqList requestList)
			throws SerialException, SQLException, IOException {
		MedicationsCreationResponse response = new MedicationsCreationResponse();
		if (requestList != null) {
			for (MedicationsCreationRequest request : requestList.getMedicationList()) {
				validateSaveMedication(request);
				Medication medication = new Medication();
				MultipartFile file = request.getFile();
				if (file != null) {
					String fileName = org.springframework.util.StringUtils.cleanPath(file.getOriginalFilename());
					log.info("saveMedication fileName :" + fileName);
					Blob blob = new SerialBlob(file.getBytes());
					medication.setImage(blob);
				}
				medication.setCode(request.getCode());
				medication.setCreatedDate(new Date());
				medication.setName(request.getName());
				medication.setWeight(request.getWeight());

				medicationRepository.save(medication);
				log.info("Medication Data Saved Successfully :" + medication);
				response.setStatus(Constants.SUCCESS);
			}
		} else {
			new DroneException("No Record Found To Be Saved In Medication Table");
		}
		return response;
	}

	public void validateSaveMedication(MedicationsCreationRequest request) {
		String regexName = "\\b[a-zA-Z0-9._-]+$";
		String regexCode = "\\b[A-Z0-9._]+$";

		if (request.getCode() != null) {
			if (!request.getCode().matches(regexCode)) {
				new DroneException("allowed only upper-case letters, underscore and numbers for Code Field");
			}
		}
		if (request.getName() != null) {
			if (!request.getName().matches(regexName)) {
				new DroneException("allowed only letters, numbers, ‘-‘, ‘_’ for Name Field");
			}
		}

	}

}
