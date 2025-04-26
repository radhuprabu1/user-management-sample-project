package com.springboot.usermanagement.exception;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * GlobalExceptionHandler is a special class used to handle exceptions globally across the entire application.
 * 
 * <p>Instead of writing repetitive try-catch blocks in controllers, we use this class to catch and handle
 * exceptions in a centralized way using the {@code @ControllerAdvice} annotation.</p>
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

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
	
	/**
	 * Handles validation errors for method arguments annotated with validation constraints.
	 * It processes the validation errors, extracts field-specific error messages, and returns 
	 * a structured response containing the error details.
	 * @param e       the exception that contains details about the validation failure.
	 *                Typically occurs when method arguments fail to meet specified constraints.
	 * @param headers the HTTP headers associated with the request. Not used directly in this implementation.
	 * @param status  the HTTP status code associated with the validation error. Not used directly here,
	 *                as a static {@code BAD_REQUEST} status is returned.
	 * @param request the web request that triggered the validation failure. Provides contextual information
	 *                about the request, but is not explicitly utilized.
	 * @return a {@code ResponseEntity<Object>} containing a map of validation errors. Each entry in the map 
	 *         corresponds to a field name and its associated error message. The response has an HTTP status 
	 *         of {@code BAD_REQUEST}.
	 * 
	 * @apiNote This method simplifies error handling by organizing validation errors into a readable format.
	 *          Applications can use this response to inform users about specific issues with their input.
	 * @see MethodArgumentNotValidException
	 */
	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException e, 
                                                                  HttpHeaders headers, 
                                                                  HttpStatusCode status,
                                                                  WebRequest request) {
        Map<String, String> errors = new HashMap<>();
        List<ObjectError> errorList = e.getBindingResult().getAllErrors();

        errorList.forEach(error ->{
            String fieldName = ((FieldError) error).getField();
            String message = error.getDefaultMessage();
            errors.put(fieldName, message);
        });

        return new ResponseEntity<>(errors, HttpStatus.BAD_REQUEST);
    }
}