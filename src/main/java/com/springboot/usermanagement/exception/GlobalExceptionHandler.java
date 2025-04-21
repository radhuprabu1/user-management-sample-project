package com.springboot.usermanagement.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;

/**
 * GlobalExceptionHandler is a special class used to handle exceptions globally across the entire application.
 * 
 * <p>Instead of writing repetitive try-catch blocks in controllers, we use this class to catch and handle
 * exceptions in a centralized way using the {@code @ControllerAdvice} annotation.</p>
 */
@ControllerAdvice
public class GlobalExceptionHandler {

	/**
	 * @ExceptionHandler(...): Catches specific types of exceptions and handles them
	 */
	@ExceptionHandler(ResourceNotFoundException.class)
	/**
	 * Handles {@link ResourceNotFoundException} when a requested user is not found.
	 * 
	 * @param e The ResourceNotFoundException thrown.
	 * @param wr WebRequest that provides request-related information.
	 * @return A ResponseEntity containing the error details and HTTP status code 404 (Not Found).
	 */
	public ResponseEntity<ErrorDetails> handleResourceNotFoundException(ResourceNotFoundException e,
			WebRequest wr) {
		ErrorDetails errDetails = new ErrorDetails(
				LocalDateTime.now(),                      // Timestamp of the error
				e.getMessage(),                           // Error message from the exception
				wr.getDescription(false),                 // The request path where the error occurred
				"USER_NOT_FOUND"                          // Custom error code
				);
		return new ResponseEntity<>(errDetails, HttpStatus.NOT_FOUND);
	}

	/**
	 * Handles {@link EmailAlreadyExistsException} when a user tries to register with an existing email.
	 * 
	 * @param e The EmailAlreadyExistsException thrown.
	 * @param wr WebRequest that provides request-related information.
	 * @return A ResponseEntity containing the error details and HTTP status code 400 (Bad Request).
	 */
	@ExceptionHandler(EmailAlreadyExistsException.class)
	public ResponseEntity<ErrorDetails> handleEmailAlreadyExistsException(EmailAlreadyExistsException e,
			WebRequest wr) {
		ErrorDetails errDetails = new ErrorDetails(
				LocalDateTime.now(),
				e.getMessage(),
				wr.getDescription(false),
				"EMAIL ID ALREADY EXISTS FOR ANOTHER USER"
				);
		return new ResponseEntity<>(errDetails, HttpStatus.BAD_REQUEST);
	}

	/**
	 * Handles all other exceptions that are not specifically handled above.
	 * 
	 * @param e The exception thrown.
	 * @param wr WebRequest that provides request-related information.
	 * @return A ResponseEntity containing the error details and HTTP status code 500 (Internal Server Error).
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<ErrorDetails> handleGlobalException(Exception e, WebRequest wr) {
		ErrorDetails errDetails = new ErrorDetails(
				LocalDateTime.now(),
				e.getMessage(),
				wr.getDescription(false),
				"INTERNAL SERVER ERROR"
				);
		return new ResponseEntity<>(errDetails, HttpStatus.INTERNAL_SERVER_ERROR);
	}
}