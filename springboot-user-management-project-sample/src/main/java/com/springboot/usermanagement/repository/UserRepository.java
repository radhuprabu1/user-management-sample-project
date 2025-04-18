package com.springboot.usermanagement.repository;

import com.springboot.usermanagement.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

/**
 * UserRepository is a Data Access Layer interface that handles
 * all interactions with the database for the {@link User} entity.
 * 
 * It extends {@link JpaRepository}, which provides many built-in
 * CRUD (Create, Read, Update, Delete) methods out-of-the-box.
 * 
 * Spring Data JPA automatically implements this interface at runtime,
 * so you don't have to write any SQL or implementation code for basic operations.
 *
 * Example usage (in service layer):
 * - userRepository.save(user);       // to insert or update
 * - userRepository.findById(id);     // to fetch by ID
 * - userRepository.findAll();        // to fetch all users
 * - userRepository.deleteById(id);   // to delete a user
 */
public interface UserRepository extends JpaRepository<User, Long> {

}