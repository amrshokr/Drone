package com.app.drone.Exceptions;

import java.time.ZonedDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class DroneExceptionHandler {
	
	@ExceptionHandler(value = {DroneException.class})
	public ResponseEntity<Object> handleDroneException(DroneException e){
		
		HttpStatus httpStatus=HttpStatus.BAD_REQUEST;
		
		Exception exception = new Exception(
				e.getMessage(),
				httpStatus,
				ZonedDateTime.now()
				); 
		return new ResponseEntity<Object>(exception,httpStatus);
	}
	
}
