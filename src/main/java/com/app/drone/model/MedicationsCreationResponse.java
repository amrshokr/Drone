package com.app.drone.model;

public class MedicationsCreationResponse {
	private String status;

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}
	
	public MedicationsCreationResponse(String status){
		this.status=status;
	}

	public MedicationsCreationResponse() {
		super();
	}
	
	
}
