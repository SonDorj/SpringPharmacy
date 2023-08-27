package com.pharma.exception;

import java.util.Date;

import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice // all controllers are proxied with throws advice [exception handling]
public class CustomizedResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler(Exception.class)
	public final ResponseEntity<Object> handleAllExceptions(Exception ex, WebRequest request) {
		GenericExceptionResponse genericExceptionResponse = new GenericExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<Object>(genericExceptionResponse, HttpStatus.INTERNAL_SERVER_ERROR);
	}

	@ExceptionHandler(PatientNotFoundException.class)
	public final ResponseEntity<Object> handleCustomerNotFoundException(PatientNotFoundException ex,
			WebRequest request) {
		GenericExceptionResponse genericExceptionResponse = new GenericExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<Object>(genericExceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(MedicineNotFoundException.class)
	public final ResponseEntity<Object> handleCustomerNotFoundException(MedicineNotFoundException ex,
			WebRequest request) {
		GenericExceptionResponse genericExceptionResponse = new GenericExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<Object>(genericExceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(OrderNotFoundException.class)
	public final ResponseEntity<Object> handleCustomerNotFoundException(OrderNotFoundException ex, WebRequest request) {
		GenericExceptionResponse genericExceptionResponse = new GenericExceptionResponse(new Date(), ex.getMessage(),
				request.getDescription(false));
		return new ResponseEntity<Object>(genericExceptionResponse, HttpStatus.NOT_FOUND);
	}

	@ExceptionHandler(EmptyResultDataAccessException.class)
	public ResponseEntity<String> noCityFound(DataAccessException e) {

		return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No Customer found");
	}

}
