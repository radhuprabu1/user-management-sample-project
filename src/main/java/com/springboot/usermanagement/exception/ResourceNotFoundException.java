package com.springboot.usermanagement.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * ResourceNotFoundException is thrown when a resource (e.g., user) is not found in the database.
 * 
 * <p>By using the {@code @ResponseStatus} annotation, this exception is automatically associated with
 * the HTTP status code 404 (Not Found) when it is thrown, which allows us to return an appropriate response to the client.</p>
 * 
 * For example, when a user is requested by ID but doesn't exist in the database, this exception will be thrown.
 */
@ResponseStatus(value = HttpStatus.NOT_FOUND) // @ResponseStatus associates an exception with an HTTP status code.
// Whenever the exception is thrown, Spring automatically sets the specified status code (404 Not Found) in the response.
public class ResourceNotFoundException extends RuntimeException {

	private static final long serialVersionUID = 5938680883118381717L;

	/**
	 * Name of the resource that was not found (e.g., "User").
	 */
	private String resourceName;

	/**
	 * Name of the field (e.g., "id") used for finding the resource.
	 */
	private String fieldName;

	/**
	 * The value of the field used for finding the resource (e.g., the specific user ID).
	 */
	private Long fieldValue;

	/**
	 * Constructor that creates a ResourceNotFoundException with a specific resource, field, and field value.
	 * 
	 * @param resourceName The name of the resource that was not found (e.g., "User").
	 * @param fieldName The name of the field used to find the resource (e.g., "id").
	 * @param fieldValue The value of the field used to find the resource (e.g., the user ID).
	 */
	public ResourceNotFoundException(String resourceName, String fieldName, Long fieldValue) {
		// Call the superclass (RuntimeException) constructor with a custom error message.
		super(String.format(
				"%s not found with %s : '%s'",  // Format the message to describe the resource not found
				resourceName, fieldName, fieldValue));

		this.resourceName = resourceName;
		this.fieldName = fieldName;
		this.fieldValue = fieldValue;
	}
}