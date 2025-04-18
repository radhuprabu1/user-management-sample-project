package com.springboot.usermanagement.service.impl;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.springboot.usermanagement.entity.User;
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
    public User createUser(User user) {
        // Save the user to the database using Spring Data JPA
        return userRepository.save(user);
    }

    /**
     * Retrieves a user by their ID.
     * 
     * @param userId The ID of the user to be retrieved.
     * @return The User object if found, otherwise throws NoSuchElementException.
     */
    @Override
    public User getUserById(Long userId) throws NoSuchElementException {
        // Find the user by ID; result is wrapped in Optional to avoid null
        Optional<User> user = userRepository.findById(userId);

        // Use get() to extract the actual User from Optional
        // (Note: this throws NoSuchElementException if user is not found)
        return user.get();
    }

    /**
     * Retrieves a list of all users from the database.
     * 
     * @return A list of User objects.
     */
    @Override
    public List<User> getAllUsers() {
        // Fetch all users from the database using Spring Data JPA
        return userRepository.findAll();
    }

    /**
     * Updates an existing user's details.
     * 
     * @param user A User object containing the updated data.
     *             The user must already exist in the database.
     * @return The updated User object after saving.
     */
    @Override
    public User updateUser(User user) {
        // Retrieve the existing user from the database using the provided ID
        User existingUser = userRepository.findById(user.getId()).get();

        // Update the existing user's fields with new values
        existingUser.setEmail(user.getEmail());
        existingUser.setFirstName(user.getFirstName());
        existingUser.setLastName(user.getLastName());

        // Save the updated user back to the database
        // save() is used for both creating and updating in Spring Data JPA.
        return userRepository.save(existingUser);
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