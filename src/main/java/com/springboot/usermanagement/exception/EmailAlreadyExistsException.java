package com.springboot.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * {@code EmailAlreadyExistsException} is a custom runtime exception
 * thrown when a user tries to register or update with an email address
 * that already exists in the database.
 * 
 * <p>This helps enforce uniqueness of user emails and provides
 * meaningful error messages to the client.</p>
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
/**
 * This annotation tells Spring to return a 400 Bad Request HTTP status code
 * when this exception is thrown. It helps the client know that the request
 * was not valid due to duplicate email.
 */
public class EmailAlreadyExistsException extends RuntimeException {

	/**
	 * A message describing the specific reason for the exception.
	 */
	private String message;

	/**
	 * Constructor to create a new {@code EmailAlreadyExistsException}
	 * with a custom error message.
	 * 
	 * @param message The error message to be returned to the client.
	 */
	public EmailAlreadyExistsException(String message) {
		super(message); // Passes the message to the base RuntimeException class
	}
}