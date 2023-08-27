package com.pharma.exception;

// check the error - 500
// explicitly set 404 error by using @ResponseStatus
//@ResponseStatus(HttpStatus.NOT_FOUND)
public class OrderNotFoundException extends RuntimeException{

	private static final long serialVersionUID = 1L;

	public OrderNotFoundException(String message) {
		super(message);
		}

}
