package com.app.drone.model;

public enum StateType {
	
	IDLE("IDLE"), LOADING("LOADING"), LOADED("LOADED"), DELIVERING("DELIVERING"), DELIVERED("DELIVERED"), RETURNING("RETURNING");
	
	StateType(String name){
		this.name=name;
	}
	private String name;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	

}
