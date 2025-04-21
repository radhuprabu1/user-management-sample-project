package com.springboot.usermanagement.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * UserDto is a Data Transfer Object (DTO) used to transfer user data
 * between the client and the server.
 * 
 * It contains only the essential fields required for creating,
 * reading, updating, or deleting user information without exposing
 * internal entities directly.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {
	
	private Long id;
	private String firstName;
	private String lastName;
	private String email;

}
