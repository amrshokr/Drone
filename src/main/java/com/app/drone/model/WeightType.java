package com.app.drone.model;

public enum WeightType {
	
	LIGHTWEIGHT("Lightweight"), MIDDLEWEIGHT("Middleweight"), CRUISERWEIGHT("Cruiserweight"), HEAVYWEIGHT("Heavyweight");
	
	WeightType(String name){
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
