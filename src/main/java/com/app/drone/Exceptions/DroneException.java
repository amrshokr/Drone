package com.app.drone.Exceptions;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

public class DroneException extends RuntimeException {
	
	private Map<String, Object> extensions=new HashMap<>();
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String message;
	
	
	
	public DroneException(String message,BigDecimal id) {
		super(message);
		this.message = message;
		extensions.put("invalidId", id);
		return;
	}
	
	public DroneException(String message) {
		super(message);
		this.message = message;
		extensions.put("invalidId",null);
		return;
	}
	
	public DroneException(String message,Throwable cause) {
		super(message,cause);
		this.message = message;
		extensions.put("invalidId",null);
		return;
	}
}
