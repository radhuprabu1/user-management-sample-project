package com.springboot.usermanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

import com.springboot.usermanagement.entity.User;
import com.springboot.usermanagement.service.UserService;

import lombok.AllArgsConstructor;

/**
 * UserController is a Spring MVC Controller that handles all REST API endpoints
 * related to user management.
 * 
 * It supports the following operations:
 * - Create a new user
 * - Retrieve a user by ID
 * - Retrieve all users
 * - Update an existing user
 * - Delete a user by ID
 * 
 * All endpoints are prefixed with "/api/users".
 */
@Controller // It tells Spring that this class handles HTTP requests.
@AllArgsConstructor
@RequestMapping("api/users") // sets the base path for all methods inside.
public class UserController {

	/**
	 * UserService is used to perform business logic and interact with the data layer.
	 */
	private UserService userService;

	/**
	 * Create a new user.
	 * RequestBody - binds incoming request data to method parameters.
	 * @param user The user object sent in the request body (in JSON format).
	 * @return ResponseEntity containing the saved user and HTTP status code 201 (Created).
	 */
	@PostMapping
	public ResponseEntity<User> createUser(@RequestBody User user) {
		// Call the service layer to save the user to the database
		User savedUser = userService.createUser(user);
		// Return the saved user along with a 201 Created response
		return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
	}

	/**
	 * Retrieve a user by their ID.
	 * 
	 * Example URL: GET http://localhost:8080/api/users/1
	 * PathVariable Annotation binds incoming request data to method parameters.
	 * @param userId The ID of the user to retrieve (from the URL path).
	 * @return ResponseEntity containing the user (if found) and HTTP status code 200 (OK).
	 */
	@GetMapping("{id}")
	public ResponseEntity<User> getUserById(@PathVariable("id") Long userId) {
		// Get the user with the given ID from the service layer
		User getUser = userService.getUserById(userId);
		// Return the user along with a 200 OK response
		return new ResponseEntity<>(getUser, HttpStatus.OK);
	}

	/**
	 * Retrieve all users.
	 * 
	 * Example URL: GET http://localhost:8080/api/users
	 * ResponseEntity<T> is used to return both data and HTTP status codes cleanly
	 * @return ResponseEntity containing the list of all users and HTTP status code 200 (OK).
	 */
	@GetMapping
	public ResponseEntity<List<User>> getAllUsers() {
		// Fetch the list of all users from the service layer
		List<User> getUsers = userService.getAllUsers();
		// Return the list along with a 200 OK response
		return new ResponseEntity<>(getUsers, HttpStatus.OK);
	}

	/**
	 * Update an existing user.
	 * 
	 * Example URL: PUT http://localhost:8080/api/users/1
	 * 
	 * @param userId The ID of the user to update (from the URL path).
	 * @param user The updated user data (sent in the request body).
	 * @return ResponseEntity containing the updated user and HTTP status code 200 (OK).
	 */
	@PutMapping("{id}")
	public ResponseEntity<User> updateUser(@PathVariable("id") Long userId,
			@RequestBody User user) {
		// Set the user ID in the incoming user object
		user.setId(userId);
		// Call the service layer to update the user in the database
		User updatedUser = userService.updateUser(user);
		// Return the updated user along with a 200 OK response
		return new ResponseEntity<>(updatedUser, HttpStatus.OK);
	}

	/**
	 * Delete a user by their ID.
	 * 
	 * Example URL: DELETE http://localhost:8080/api/users/1
	 * 
	 * @param userId The ID of the user to delete (from the URL path).
	 * @return ResponseEntity containing a success message and HTTP status code 200 (OK).
	 */
	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
		// Call the service layer to delete the user from the database
		userService.deleteUser(userId);
		// Return a success message along with a 200 OK response
		return new ResponseEntity<>("User Successfully Deleted!", HttpStatus.OK);
	}
}