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
	
	public DroneException(String message,BigDecimal id) {
		super(message);
		extensions.put("invalidId", id);
	}
	
	public DroneException(String message) {
		super(message);
		extensions.put("invalidId",null);
	}
}
