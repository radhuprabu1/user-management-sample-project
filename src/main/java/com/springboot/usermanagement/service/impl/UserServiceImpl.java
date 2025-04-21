package com.springboot.usermanagement.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.springboot.usermanagement.dto.UserDto;
import com.springboot.usermanagement.entity.User;
import com.springboot.usermanagement.exception.EmailAlreadyExistsException;
import com.springboot.usermanagement.exception.ResourceNotFoundException;
import com.springboot.usermanagement.mapper.UserMapper;
import com.springboot.usermanagement.repository.UserRepository;
import com.springboot.usermanagement.service.UserService;

import lombok.AllArgsConstructor;

/**
 * {@code UserServiceImpl} is a service class that provides the implementation
 * of the {@link UserService} interface.
 * 
 * <p>This class contains business logic to manage user-related operations:
 * creating a new user, retrieving users (by ID or all), updating user details,
 * and deleting users.</p>
 * 
 * <p>It interacts with the database using {@link UserRepository}, and maps
 * between DTOs and Entities using {@link UserMapper}.</p>
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	/**
	 * Repository for interacting with the User table in the database.
	 */
	private UserRepository userRepository;

	/**
	 * Creates a new user after checking if the email already exists.
	 *
	 * @param userDto The data transfer object containing user details.
	 * @return The saved user's details as a UserDto.
	 * @throws EmailAlreadyExistsException if a user with the same email already exists.
	 */
	@Override
	public UserDto createUser(UserDto userDto) {
		// Check if a user with the given email already exists
		Optional<User> newUser = userRepository.findByEmail(userDto.getEmail());
		if (newUser.isPresent()) {
			throw new EmailAlreadyExistsException("Email Already Exists for another user");
		}

		// Convert DTO to Entity object to store in DB
		User user = UserMapper.mapToUser(userDto);

		// Save the user and get the saved entity with ID
		User savedUser = userRepository.save(user);

		// Convert saved entity back to DTO to return
		return UserMapper.mapToUserDto(savedUser);
	}

	/**
	 * Retrieves a user by their unique ID.
	 *
	 * @param userId The ID of the user to retrieve.
	 * @return The user's details as a UserDto.
	 * @throws ResourceNotFoundException if the user does not exist.
	 */
	@Override
	public UserDto getUserById(Long userId) throws NoSuchElementException {
		// Try to find the user by ID, or throw custom exception if not found
		User user = userRepository.findById(userId)
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userId));

		// Convert the found entity into DTO for response
		return UserMapper.mapToUserDto(user);
	}

	/**
	 * Retrieves all users from the database.
	 *
	 * @return A list of UserDto objects containing all user details.
	 */
	@Override
	public List<UserDto> getAllUsers() {
		// Fetch all user entities from the database
		List<User> allUsers = userRepository.findAll();

		// Convert each entity into DTO using streams and return
		return allUsers.stream()
				.map(UserMapper::mapToUserDto)
				.collect(Collectors.toList());
	}

	/**
	 * Updates an existing user's details.
	 *
	 * @param userDto A DTO containing updated user details.
	 *                The user ID must be present and valid.
	 * @return The updated user details as a UserDto.
	 * @throws ResourceNotFoundException if the user does not exist.
	 */
	@Override
	public UserDto updateUser(UserDto userDto) {
		// Find the user to update; if not found, throw exception
		User existingUser = userRepository.findById(userDto.getId())
				.orElseThrow(() -> new ResourceNotFoundException("User", "id", userDto.getId()));

		// Set new values into the existing entity
		existingUser.setEmail(userDto.getEmail());
		existingUser.setFirstName(userDto.getFirstName());
		existingUser.setLastName(userDto.getLastName());

		// Save the updated entity
		User updatedUser = userRepository.save(existingUser);

		// Convert the updated entity back to DTO
		return UserMapper.mapToUserDto(updatedUser);
	}

	/**
	 * Deletes a user from the database using their ID.
	 *
	 * @param userId The ID of the user to delete.
	 * @throws ResourceNotFoundException if the user with the given ID does not exist.
	 */
	@Override
	public void deleteUser(Long userId) {
		// Check if the user exists before deleting
		userRepository.findById(userId).orElseThrow(
				() -> new ResourceNotFoundException("User", "id", userId));

		// Delete the user by ID
		userRepository.deleteById(userId);
	}
}