package com.app.drone.model;

import java.math.BigDecimal;
import java.sql.Blob;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.Table;

@Entity
@Table(name="MEDICATION")
public class Medication {

	private BigDecimal medicationId;
	private String name;
	private BigDecimal weight;
	private String code;
	private Blob image;
	private Date createdDate;
	
	@Id
	@Column(name="MEDICATION_ID")
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	public BigDecimal getMedicationId() {
		return medicationId;
	}
	public void setMedicationId(BigDecimal medicationId) {
		this.medicationId = medicationId;
	}
	@Column(name="MEDICATION_NAME")
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	@Column(name="WEIGHT")
	public BigDecimal getWeight() {
		return weight;
	}
	public void setWeight(BigDecimal weight) {
		this.weight = weight;
	}
	@Column(name="MEDICATION_CODE")
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	@Lob
	@Column(name="IMAGE")
	public Blob getImage() {
		return image;
	}
	public void setImage(Blob image) {
		this.image = image;
	}
	
	@Column(name="CREATED_DATE")
	public Date getCreatedDate() {
		return createdDate;
	}

	
	public void setCreatedDate(Date createdDate) {
		this.createdDate = createdDate;
	}
	
	public Medication(String name, BigDecimal weight, String code, Blob image, Date createdDate) {
		super();
		this.name = name;
		this.weight = weight;
		this.code = code;
		this.image = image;
		this.createdDate = createdDate;
	}
	
	public Medication() {
		super();
	}
	@Override
	public String toString() {
		return "Medication [medicationId=" + medicationId + ", name=" + name + ", weight=" + weight + ", code=" + code
				+ ", image=" + image + ", createdDate=" + createdDate + "]";
	}
	
	
	
	
}
