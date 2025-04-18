package com.springboot.usermanagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * The User class is a JPA Entity that maps to a database table named "users".
 * It represents a user in the system with basic fields like id, first name, last name, and email.
 * 
 * This class uses Lombok to reduce boilerplate code (getters, setters, constructors).
 */
@Getter // Lombok: Automatically generates getter methods for all fields
@Setter // Lombok: Automatically generates setter methods for all fields
@NoArgsConstructor // Lombok: Generates a default (no-argument) constructor
@AllArgsConstructor // Lombok: Generates a constructor with arguments for all fields
@Entity(name = "users") // JPA: Marks this class as a JPA entity and maps it to a table named "users"
public class User {

	/**
	 * The unique ID for each user.
	 * 
	 * @Id - JPA: Marks this field as the primary key for the entity.
	 * @GeneratedValue - JPA: Automatically generates the value for this ID when a user is saved.
	 *     strategy = GenerationType.IDENTITY means the database will auto-increment the ID.
	 */
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	/**
	 * First name of the user. Cannot be null.
	 * 
	 * @Column - JPA: Maps this field to a column in the database.
	 *     nullable = false means this column must have a value (cannot be null).
	 */
	@Column(nullable = false)
	private String firstName;

	/**
	 * Last name of the user. Cannot be null.
	 * 
	 * @Column - JPA: Maps this field to a database column.
	 *     nullable = false means this field must have a value.
	 */
	@Column(nullable = false)
	private String lastName;

	/**
	 * Email of the user. Cannot be null and must be unique.
	 * 
	 * @Column - JPA: Maps to a database column.
	 *     nullable = false => email must be provided.
	 *     unique = true => no two users can have the same email in the database.
	 */
	@Column(nullable = false, unique = true)
	private String email;
}