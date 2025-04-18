package com.springboot.usermanagement.mapper;

import com.springboot.usermanagement.dto.UserDto;
import com.springboot.usermanagement.entity.User;

public class UserMapper {
	
	// Private constructor to prevent instantiation
    private UserMapper() {
        throw new UnsupportedOperationException("This is a utility class and cannot be instantiated");
    }


    // Convert User JPA Entity into UserDto
    public static UserDto mapToUserDto(User user){
        return new UserDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }

    // Convert UserDto into User JPA Entity
    public static User mapToUser(UserDto userDto){
        return new User(
                userDto.getId(),
                userDto.getFirstName(),
                userDto.getLastName(),
                userDto.getEmail()
        );
    }
}
