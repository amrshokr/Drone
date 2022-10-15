package com.app.drone.manager;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Blob;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.sql.rowset.serial.SerialBlob;
import javax.sql.rowset.serial.SerialException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import com.app.drone.Exceptions.DroneException;
import com.app.drone.model.Drone;
import com.app.drone.model.DroneCreationRequest;
import com.app.drone.model.DroneCreationResponse;
import com.app.drone.model.Medication;
import com.app.drone.model.MedicationsCreationRequest;
import com.app.drone.model.MedicationsCreationResponse;
import com.app.drone.model.MidicationCreationReqList;
import com.app.drone.model.StateType;
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

	public DroneCreationResponse saveDrone(DroneCreationRequest request) {
		DroneCreationResponse droneResponse = new DroneCreationResponse();
		if (request != null) {
			validateDroneSaveRequest(request);
			Drone drone = new Drone();

			drone.setSerialNumber(request.getSerialNumber());
			drone.setState(StateType.IDLE.getName());
			drone.setBatteryCapacity(request.getBatteryCapacity());
			drone.setCreatedDate(new Date());
			drone.setModel(request.getModel().getName());
			drone.setWeightLimit(request.getWeightLimit());

			droneRepository.save(drone);
			droneResponse.setStatus(Constants.SUCCESS);
			log.info("Drone Data Saved Successfully :" + drone);

		} else {
			droneResponse.setStatus(Constants.NOT_SUCCESS);
			throw new DroneException("No Record Found");
		}
		return droneResponse;

	}

	public void validateDroneSaveRequest(DroneCreationRequest request) {
		if (request != null) {

			if (request.getSerialNumber() != null) {
				if (request.getSerialNumber().length() > 100) {
					throw new DroneException("Serial Number max characters is 100");
				}
				if (droneRepository.findBySerialNumber(request.getSerialNumber()) != null) {
					throw new DroneException("Serial Number Already Exist for Other Drone");
				}

			} else {
				throw new DroneException("Please enter a valid percentage for Battry Capacity");
			}
			if (request.getBatteryCapacity() != null) {
				if (request.getBatteryCapacity().compareTo(BigDecimal.ZERO) > 0
						&& request.getBatteryCapacity().compareTo(new BigDecimal(100)) < 0) {

				} else {
					throw new DroneException("Please enter a valid percentage for Battry Capacity");
				}
			}
			if (request.getWeightLimit() != null) {
				if (request.getWeightLimit().compareTo(Constants.WIGHT_LIMIT) <= 0
						&& request.getWeightLimit().compareTo(BigDecimal.ZERO) > 0) {

				} else {
					throw new DroneException("Please enter a valid wieght limit 500 max");
				}
			}

		} else {
			throw new DroneException("No Record Found To Be Saved In Drone Table");
		}
	}

	public MedicationsCreationResponse saveMedication(MedicationsCreationRequest request)
			throws SerialException, SQLException, IOException {
		MedicationsCreationResponse response = new MedicationsCreationResponse();
		List<Drone> droneList = droneRepository.getFreeDrones();
		if (request != null) {
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
			BigDecimal loadedWeight = loadDroneWithMedecation(droneList, medication.getWeight(), medication.getName());
			Drone loadDrone = new Drone();
			loadDrone.setDroneId(loadedWeight);
			medication.setDrone(loadDrone);
			medicationRepository.save(medication);

			log.info("Medication Data Saved Successfully :" + medication);
			response.setStatus(Constants.SUCCESS);
			startTheDrones(droneList);
		} else {
			throw new DroneException("No Record Found To Be Saved In Medication Table");
		}
		return response;
	}

	public void validateSaveMedication(MedicationsCreationRequest request) {
		String regexName = "\\b[a-zA-Z0-9._-]+$";
		String regexCode = "\\b[A-Z0-9._]+$";

		if (request.getCode() != null) {
			if (!request.getCode().matches(regexCode)) {
				throw new DroneException("allowed only upper-case letters, underscore and numbers for Code Field");
			}
		}
		if (request.getName() != null) {
			if (!request.getName().matches(regexName)) {
				throw new DroneException("allowed only letters, numbers, ‘-‘, ‘_’ for Name Field");
			}
		}
		if (request.getWeight() == null || request.equals(BigDecimal.ZERO)) {
			throw new DroneException("Please enter the weight for the medication.");
		}
		log.info("Code "+request.getCode());
		List<Medication> medByCode=medicationRepository.getByCode(request.getCode());
		
		if(medByCode!=null && !medByCode.isEmpty()) {
			throw new DroneException("Code Already Exist for Other medication.");
		}
		List<Medication> medByName=medicationRepository.getByName(request.getName());
		if(medByName!=null && !medByName.isEmpty()) {
			throw new DroneException("Name Already Exist for Other medication.");
		}
		

	}

	public BigDecimal loadDroneWithMedecation(List<Drone> droneList, BigDecimal weight, String medicationName) {
		BigDecimal droneId = BigDecimal.ZERO;
		for (int i = 0; i < droneList.size(); i++) {
			Drone drone = droneList.get(i);
			if (drone.getBatteryCapacity().compareTo(Constants.BATTERY_LIMIT) > 0) {
				if (drone.getLoadedWeight() != null) {
					if (weight.add(drone.getLoadedWeight()).compareTo(drone.getWeightLimit()) < 0) {
						droneList.get(i).setLoadedWeight(drone.getLoadedWeight().add(weight));
						droneId = drone.getDroneId();
						break;
					}
				} else {
					if (weight.compareTo(drone.getWeightLimit()) < 0) {
						droneList.get(i).setLoadedWeight(weight);
						droneId = drone.getDroneId();
						break;
					}
				}
			} else {
				log.info("Battry Level Lower Than 25%, We can't load medication on it. Serial Number:"
						+ drone.getSerialNumber());
			}
		}
		if (droneId.equals(BigDecimal.ZERO)) {
			log.info("There is no Drone Avalaible for this Medication :" + medicationName);
		} else {
			log.info("Loading medication Name: " + medicationName);
		}
		return droneId;
	}

	public void startTheDrones(List<Drone> droneList) {
		for (Drone drone : droneList) {
			if (drone.getLoadedWeight() != null && drone.getLoadedWeight() !=BigDecimal.ZERO) {
				drone.setState(StateType.LOADING.getName());
			}
			droneRepository.save(drone);
		}

	}

	@Scheduled(fixedRate = 36000)
	public void droneTask() {
		List<Drone> droneList = droneRepository.findAll();
		// To Get Medication List which is not loaded yet
		List<Medication> notLoadedMedicationList = medicationRepository.getNotLoadedMedicationList();
		if (droneList != null && !droneList.isEmpty()) {
			for (Drone drone : droneList) {
				if (drone.getState().equalsIgnoreCase(StateType.LOADING.getName())) {
					drone.setState(StateType.LOADED.getName());
					log.info("Drone In LOADED State Serial " + drone.getSerialNumber() + " And Battry Capacity is: "
							+ drone.getBatteryCapacity());
				} else if (drone.getState().equalsIgnoreCase(StateType.LOADED.getName())) {
					drone.setState(StateType.DELIVERING.getName());
					log.info("Drone In DELIVERING State Serial " + drone.getSerialNumber() + " And Battry Capacity is: "
							+ drone.getBatteryCapacity());
				} else if (drone.getState().equalsIgnoreCase(StateType.DELIVERING.getName())) {
					drone.setState(StateType.DELIVERED.getName());
					log.info("Drone In DELIVERED State Serial " + drone.getSerialNumber() + " And Battry Capacity is: "
							+ drone.getBatteryCapacity());
				} else if (drone.getState().equalsIgnoreCase(StateType.DELIVERED.getName())) {
					drone.setState(StateType.RETURNING.getName());
					log.info("Drone In RETURNING State Serial " + drone.getSerialNumber() + " And Battry Capacity is: "
							+ drone.getBatteryCapacity());
				} else if (drone.getState().equalsIgnoreCase(StateType.RETURNING.getName())) {
					// Drone Will Get charged after RETURNING Status and the Load will get free
					drone.setState(StateType.IDLE.getName());
					drone.setLoadedWeight(BigDecimal.ZERO);
					drone.setBatteryCapacity(new BigDecimal(100));
					log.info("Drone In IDLE State Serial " + drone.getSerialNumber() + " And Battry Capacity is: "
							+ drone.getBatteryCapacity());
				} else if (drone.getState().equalsIgnoreCase(StateType.IDLE.getName())
						&& drone.getLoadedWeight() != BigDecimal.ZERO) {
					drone.setState(StateType.LOADING.getName());
				} else {
					log.info("Drone In Idle State Serial " + drone.getSerialNumber() + " And Battry Capacity is: "
							+ drone.getBatteryCapacity());
					if (notLoadedMedicationList != null && !notLoadedMedicationList.isEmpty()) {
						for (int i = 0; i < notLoadedMedicationList.size(); i++) {
							if (notLoadedMedicationList.get(i).getDrone() == null) {
								if (drone.getBatteryCapacity().compareTo(Constants.BATTERY_LIMIT) > 0) {
									if (drone.getLoadedWeight() != null) {
										if (notLoadedMedicationList.get(i).getWeight().add(drone.getLoadedWeight())
												.compareTo(drone.getWeightLimit()) < 0) {
											drone.setLoadedWeight(drone.getLoadedWeight()
													.add(notLoadedMedicationList.get(i).getWeight()));
											notLoadedMedicationList.get(i).setDrone(drone);
											log.info("Loading medication Name: "
													+ notLoadedMedicationList.get(i).getName());
											medicationRepository.save(notLoadedMedicationList.get(i));
										}
									} else {
										if (notLoadedMedicationList.get(i).getWeight()
												.compareTo(drone.getWeightLimit()) < 0) {
											drone.setLoadedWeight(notLoadedMedicationList.get(i).getWeight());
											notLoadedMedicationList.get(i).setDrone(drone);
											log.info("Loading medication Name: "
													+ notLoadedMedicationList.get(i).getName());
											medicationRepository.save(notLoadedMedicationList.get(i));
										}
									}
								} else {
									log.info(
											"Battry Level Lower Than 25%, We can't load medication on it. Serial Number:"
													+ drone.getSerialNumber());
								}
							}
						}
					}
				}
				droneRepository.save(drone);
			}

		} else {
			log.info("There is no Drones Registered ");
		}

		System.out.println("Working");
	}

}
