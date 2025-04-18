package com.springboot.usermanagement.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.springboot.usermanagement.dto.UserDto;
import com.springboot.usermanagement.entity.User;
import com.springboot.usermanagement.mapper.UserMapper;
import com.springboot.usermanagement.repository.UserRepository;
import com.springboot.usermanagement.service.UserService;

import lombok.AllArgsConstructor;

/**
 * Implementation of the {@link UserService} interface.
 * 
 * This class handles the business logic for managing users,
 * including creating, retrieving, updating, and deleting user records.
 * 
 * It uses {@link UserRepository} to perform database operations.
 * Service Annotation makes this class a Spring-managed service component.
 * AllArgsConstructor Annotation from Lombok automatically generates a constructor that initializes the userRepository.
 */
@Service
@AllArgsConstructor
public class UserServiceImpl implements UserService {

	/**
	 * Repository interface to interact with the database for User entity operations.
	 */
	private UserRepository userRepository;

	/**
	 * Saves a new user to the database.
	 * 
	 * @param user The user object to be saved.
	 * @return The saved user with a generated ID and other persisted information.
	 */
	@Override
	public UserDto createUser(UserDto userDto) {
		// Convert UserDto into User JPA Entity
		User user = UserMapper.mapToUser(userDto);
		// Save the user to the database using Spring Data JPA
		User savedUser = userRepository.save(user);
		// Convert User Entity to UserDto
		return UserMapper.mapToUserDto(savedUser);
	}

	/**
	 * Retrieves a user by their ID.
	 * 
	 * @param userId The ID of the user to be retrieved.
	 * @return The User object if found, otherwise throws NoSuchElementException.
	 */
	@Override
	public UserDto getUserById(Long userId) throws NoSuchElementException {
		
		// Find the user by ID; result is wrapped in Optional to avoid null
		Optional<User> user = userRepository.findById(userId);

		// Use get() to extract the actual User from Optional
		// (Note: this throws NoSuchElementException if user is not found)
		return UserMapper.mapToUserDto(
				user.orElseThrow(() -> new IllegalStateException("User not found"))
				);
	}

	/**
	 * Retrieves a list of all users from the database.
	 * 
	 * @return A list of User objects.
	 */
	@Override
	public List<UserDto> getAllUsers() {
		// Fetch all users from the database using Spring Data JPA
		List<User> allUsers = userRepository.findAll();
		
		return allUsers.stream()
				.map(UserMapper::mapToUserDto)
				.collect(Collectors.toList());
	}

	/**
	 * Updates an existing user's details.
	 * 
	 * @param user A User object containing the updated data.
	 *             The user must already exist in the database.
	 * @return The updated User object after saving.
	 */
	@Override
	public UserDto updateUser(UserDto userDto) {
		
		// Retrieve the existing user from the database using the provided ID
		User existingUser = userRepository.findById(userDto.getId())
				.orElseThrow(() -> 
				new IllegalStateException("User not found"));

		// Update the existing user's fields with new values
		existingUser.setEmail(userDto.getEmail());
		existingUser.setFirstName(userDto.getFirstName());
		existingUser.setLastName(userDto.getLastName());

		// Save the updated user back to the database
		// save() is used for both creating and updating in Spring Data JPA.
		
		
		return UserMapper.mapToUserDto(userRepository.save(existingUser));
	}

	/**
	 * Deletes a user from the database by their ID.
	 * 
	 * @param userId The ID of the user to delete.
	 */
	@Override
	public void deleteUser(Long userId) {
		// Delete the user by their ID using Spring Data JPA
		userRepository.deleteById(userId);
	}
}