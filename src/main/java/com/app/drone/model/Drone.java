package com.app.drone.model;

import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name="DRONE")
public class Drone {

	private BigDecimal droneId;
	private String serialNumber;
	private String model;
	private BigDecimal weightLimit;
	private BigDecimal batteryCapacity;
	private String state;
	private Date createdDate;
	private Medication medicationId;

	@Id
	@Column(name="DRONE_ID")
	public BigDecimal getDroneId() {
		return droneId;
	}

	public void setDroneId(BigDecimal droneId) {
		this.droneId = droneId;
	}

	
	@Column(length = 100,name="SERIAL_NUMBER")
	public String getSerialNumber() {
		return serialNumber;
	}

	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}

	@Column(name="MODEL")
	public String getModel() {
		return model;
	}

	public void setModel(String model) {
		this.model = model;
	}

	@Column(name="WEIGHT_LIMIT")
	public BigDecimal getWeightLimit() {
		return weightLimit;
	}

	public void setWeightLimit(BigDecimal weightLimit) {
		this.weightLimit = weightLimit;
	}

	@Column(length = 100,name="BATTERY_CAPACITY")
	public BigDecimal getBatteryCapacity() {
		return batteryCapacity;
	}

	public void setBatteryCapacity(BigDecimal batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}

	@Column(name="STATE")
	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "MEDICATION_ID")
	public Medication getMedicationId() {
		return medicationId;
	}

	public void setMedicationId(Medication medicationId) {
		this.medicationId = medicationId;
	}

	@Override
	public String toString() {
		return "Drone [droneId=" + droneId + ", serialNumber=" + serialNumber + ", model=" + model + ", weightLimit="
				+ weightLimit + ", batteryCapacity=" + batteryCapacity + ", state=" + state + ", createdDate="
				+ createdDate + "]";
	}

	
	
	
	
	
}
