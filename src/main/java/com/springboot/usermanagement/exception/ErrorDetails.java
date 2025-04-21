package com.springboot.usermanagement.exception;

import java.time.LocalDateTime;

import lombok.*;

/**
 * {@code ErrorDetails} is a simple Java object (POJO) used to send detailed error
 * information back to the client when an exception occurs in the application.
 * 
 * <p>This helps clients understand what went wrong by providing a timestamp, 
 * a meaningful message, the request path, and a custom error code.</p>
 */
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ErrorDetails {

	/**
	 * The exact date and time when the error occurred.
	 */
	private LocalDateTime timestamp;

	/**
	 * A short message describing the error.
	 */
	private String message;

	/**
	 * The URI path where the error occurred, useful for debugging.
	 */
	private String path;

	/**
	 * A custom error code (for example: "USER_NOT_FOUND", "EMAIL_EXISTS") that
	 * helps identify the type of error in a standardized way.
	 */
	private String errorCode;
}