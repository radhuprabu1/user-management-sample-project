package com.springboot.usermanagement.service;

import java.util.List;
import com.springboot.usermanagement.entity.User;

/**
 * UserService defines a contract for user-related business operations.
 * 
 * This interface is part of the **Service Layer**, which contains business logic.
 * It is implemented by a class (like {@code UserServiceImpl}) that interacts with the database
 * using a repository.
 * 
 * Having an interface helps follow good design principles such as:
 * - Loose coupling
 * - Easy testing and mocking
 * - Better readability and scalability
 */
public interface UserService {

    /**
     * Creates a new user and saves it to the database.
     *
     * @param user The user data to be saved.
     * @return The saved user object including the generated ID.
     */
    User createUser(User user);

    /**
     * Retrieves a user by their unique ID.
     *
     * @param id The ID of the user to be fetched.
     * @return The user object if found.
     */
    User getUserById(Long id);

    /**
     * Retrieves a list of all users.
     *
     * @return A list of all user objects from the database.
     */
    List<User> getAllUsers();

    /**
     * Updates an existing user's information.
     *
     * @param user A user object with updated details. The user must already exist.
     * @return The updated user object.
     */
    User updateUser(User user);

    /**
     * Deletes a user from the system using their ID.
     *
     * @param userId The ID of the user to be deleted.
     */
    void deleteUser(Long userId);

}