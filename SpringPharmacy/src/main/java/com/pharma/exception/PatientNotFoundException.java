package com.pharma.exception;

// check the error - 500
// explicitly set 404 error by using @ResponseStatus
//@ResponseStatus(HttpStatus.NOT_FOUND)
public class PatientNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public PatientNotFoundException(String message) {
		super(message);
		}

}
