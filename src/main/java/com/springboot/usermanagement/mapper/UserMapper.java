package com.springboot.usermanagement.mapper;

import com.springboot.usermanagement.dto.UserDto;
import com.springboot.usermanagement.entity.User;

/**
 * {@code UserMapper} is a utility class that provides static methods
 * to convert between {@link User} (Entity) and {@link UserDto} (Data Transfer Object).
 * 
 * <p>This helps separate the persistence layer from the service/controller layers,
 * promoting clean architecture and better data handling.</p>
 */
public class UserMapper {

	/**
	 * Private constructor to prevent instantiation.
	 * 
	 * <p>This is a utility class, so it should not be instantiated. Calling the constructor will throw an exception.</p>
	 */
	private UserMapper() {
		throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
	}

	/**
	 * Converts a {@link User} JPA entity object into a {@link UserDto}.
	 * 
	 * @param user The {@link User} entity fetched from the database.
	 * @return A {@link UserDto} containing user data for transfer between layers.
	 */
	public static UserDto mapToUserDto(User user) {
		return new UserDto(
				user.getId(),
				user.getFirstName(),
				user.getLastName(),
				user.getEmail()
				);
	}

	/**
	 * Converts a {@link UserDto} object into a {@link User} entity.
	 * 
	 * @param userDto The data transfer object received from the client.
	 * @return A {@link User} entity ready to be saved to the database.
	 */
	public static User mapToUser(UserDto userDto) {
		return new User(
				userDto.getId(),
				userDto.getFirstName(),
				userDto.getLastName(),
				userDto.getEmail()
				);
	}
}