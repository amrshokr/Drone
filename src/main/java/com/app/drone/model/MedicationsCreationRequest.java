package com.app.drone.model;

import java.math.BigDecimal;

import org.springframework.web.multipart.MultipartFile;

public class MedicationsCreationRequest {
	
	private String name;
	private BigDecimal weight;
	private String code;
	MultipartFile file;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public MultipartFile getFile() {
		return file;
	}
	public void setFile(MultipartFile file) {
		this.file = file;
	}
	@Override
	public String toString() {
		return "MedicationsCreationRequest [name=" + name + ", weight=" + weight + ", code=" + code + "]";
	}
	
	
	
	
	

}
