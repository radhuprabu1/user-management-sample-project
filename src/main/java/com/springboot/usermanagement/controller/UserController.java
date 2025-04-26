package com.springboot.usermanagement.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.springboot.usermanagement.dto.UserDto;
import com.springboot.usermanagement.service.UserService;

import jakarta.validation.Valid;
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
@RestController
/**
 * @Controller: Marks this class as a Spring MVC controller.
 * It tells Spring to scan this class for request handling methods.
 */
@AllArgsConstructor
@RequestMapping("api/users")
/**
 * @RequestMapping("api/users"): Sets a base URI path for all HTTP
 * endpoints inside this class. So all URLs will start with /api/users.
 */
public class UserController {

    /**
     * The service layer used to handle business logic for user operations.
     */
    private UserService userService;

    /**
     * Create a new user.
     *
     * @param user The UserDto object received in JSON format from the HTTP request body.
     * @return The saved user wrapped in ResponseEntity with status 201 CREATED.
     */
    @PostMapping
    /**
     * @PostMapping: Maps this method to handle HTTP POST requests.
     * Used when creating a new resource.
     */
    public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto user) {
        // Save the user using service logic
        UserDto savedUser = userService.createUser(user);
        return new ResponseEntity<>(savedUser, HttpStatus.CREATED);
    }

    /**
     * Get a user by their unique ID.
     *
     * @param userId The ID of the user from the path.
     * @return The user wrapped in ResponseEntity with status 200 OK.
     */
    @GetMapping("{id}")
    /**
     * @GetMapping("{id}"): Maps HTTP GET requests to /api/users/{id}
     * where {id} is a placeholder for the actual user ID.
     */
    public ResponseEntity<UserDto> getUserById(@PathVariable("id") Long userId) {
        // Fetch the user by ID
        UserDto getUser = userService.getUserById(userId);
        return new ResponseEntity<>(getUser, HttpStatus.OK);
    }

    /**
     * Get all users from the system.
     *
     * @return A list of users wrapped in ResponseEntity with status 200 OK.
     */
    @GetMapping
    /**
     * @GetMapping: Maps to HTTP GET requests on /api/users
     * Used for retrieving all users.
     */
    public ResponseEntity<List<UserDto>> getAllUsers() {
        List<UserDto> getUsers = userService.getAllUsers();
        return new ResponseEntity<>(getUsers, HttpStatus.OK);
    }

    /**
     * Update an existing user.
     *
     * @param userId The ID of the user to be updated (from the path).
     * @param userDto The new user data sent in the request body.
     * @return The updated user wrapped in ResponseEntity with status 200 OK.
     */
    @PutMapping("{id}")
    /**
     * @PutMapping("{id}"): Maps to HTTP PUT requests on /api/users/{id}
     * Used for updating an existing user.
     */
    public ResponseEntity<UserDto> updateUser(@PathVariable("id") Long userId,
                                              @RequestBody @Valid UserDto userDto) {
        userDto.setId(userId);
        UserDto updatedUser = userService.updateUser(userDto);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    /**
     * Delete a user by their ID.
     *
     * @param userId The ID of the user to delete (from the path).
     * @return A success message wrapped in ResponseEntity with status 200 OK.
     */
    @DeleteMapping("{id}")
    /**
     * @DeleteMapping("{id}"): Maps to HTTP DELETE requests on /api/users/{id}
     * Used for deleting a user by their ID.
     */
    public ResponseEntity<String> deleteUser(@PathVariable("id") Long userId) {
        userService.deleteUser(userId);
        return new ResponseEntity<>("User Successfully Deleted!", HttpStatus.OK);
    }
}
