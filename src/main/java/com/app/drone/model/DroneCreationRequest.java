package com.app.drone.model;

import java.math.BigDecimal;

public class DroneCreationRequest {
	
	private String serialNumber;
	private WeightType model;
	private BigDecimal weightLimit;
	private BigDecimal batteryCapacity;
	
	public String getSerialNumber() {
		return serialNumber;
	}
	public void setSerialNumber(String serialNumber) {
		this.serialNumber = serialNumber;
	}
	public WeightType getModel() {
		return model;
	}
	public void setModel(WeightType model) {
		this.model = model;
	}
	public BigDecimal getWeightLimit() {
		return weightLimit;
	}
	public void setWeightLimit(BigDecimal weightLimit) {
		this.weightLimit = weightLimit;
	}
	public BigDecimal getBatteryCapacity() {
		return batteryCapacity;
	}
	public void setBatteryCapacity(BigDecimal batteryCapacity) {
		this.batteryCapacity = batteryCapacity;
	}
	
	
	
	

}
