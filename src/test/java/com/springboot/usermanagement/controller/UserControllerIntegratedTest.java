package com.springboot.usermanagement.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import com.springboot.usermanagement.entity.User;
import com.springboot.usermanagement.repository.UserRepository;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS) //Ensures that the test instance is created once per class, not per method.
class UserControllerIntegratedTest {

    /**
     * MockMvc: Allows for simulating HTTP requests and 
     * testing the controller endpoints without starting a full HTTP server.
     */
    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private UserRepository userRepository;

    @BeforeEach
    void setup() {
        userRepository.deleteAll(); // Ensure a clean database state before each test
    }

    @Test
    void testCreateUser() throws Exception {
        String userJson = """
        {
            "firstName": "John",
            "lastName": "Doe",
            "email": "johndoe@example.com"
        }
        """;

        MvcResult result = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(userJson))
                .andExpect(status().isCreated())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Create User Response: " + responseContent);

        assertTrue(responseContent.contains("\"firstName\":\"John\""));
        assertTrue(responseContent.contains("\"lastName\":\"Doe\""));
        assertTrue(responseContent.contains("\"email\":\"johndoe@example.com\""));
    }

    @Test
    void testGetUserById() throws Exception {
        User user = new User(null, "John", "Doe", "johndoe@example.com");
        User savedUser = userRepository.save(user);

        MvcResult result = mockMvc.perform(get("/api/users/" + savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Get User By ID Response: " + responseContent);

        assertTrue(responseContent.contains("\"firstName\":\"John\""));
        assertTrue(responseContent.contains("\"lastName\":\"Doe\""));
        assertTrue(responseContent.contains("\"email\":\"johndoe@example.com\""));
    }

    @Test
    void testGetAllUsers() throws Exception {
        User user1 = new User(null, "John", "Doe", "johndoe@example.com");
        User user2 = new User(null, "Jane", "Doe", "janedoe@example.com");
        userRepository.save(user1);
        userRepository.save(user2);

        MvcResult result = mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Get All Users Response: " + responseContent);

        assertTrue(responseContent.contains("\"firstName\":\"John\""));
        assertTrue(responseContent.contains("\"firstName\":\"Jane\""));
    }

    @Test
    void testUpdateUser() throws Exception {
        User user = new User(null, "John", "Doe", "johndoe@example.com");
        User savedUser = userRepository.save(user);

        String updatedUserJson = """
        {
            "firstName": "John Updated",
            "lastName": "Doe Updated",
            "email": "updated@example.com"
        }
        """;

        MvcResult result = mockMvc.perform(put("/api/users/" + savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON)
                .content(updatedUserJson))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Update User Response: " + responseContent);

        assertTrue(responseContent.contains("\"firstName\":\"John Updated\""));
        assertTrue(responseContent.contains("\"lastName\":\"Doe Updated\""));
        assertTrue(responseContent.contains("\"email\":\"updated@example.com\""));
    }

    @Test
    void testDeleteUser() throws Exception {
        User user = new User(null, "John", "Doe", "johndoe@example.com");
        User savedUser = userRepository.save(user);

        MvcResult result = mockMvc.perform(delete("/api/users/" + savedUser.getId())
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Delete User Response: " + responseContent);

        assertEquals("User Successfully Deleted!", responseContent);
    }

    @Test
    void testCreateUserWithDuplicateEmail() throws Exception {
        User user = new User(null, "John", "Doe", "johndoe@example.com");
        userRepository.save(user);

        String duplicateUserJson = """
        {
            "firstName": "Jane",
            "lastName": "Smith",
            "email": "johndoe@example.com"
        }
        """;

        MvcResult result = mockMvc.perform(post("/api/users")
                .contentType(MediaType.APPLICATION_JSON)
                .content(duplicateUserJson))
                .andExpect(status().isBadRequest())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Duplicate Email Error Response: " + responseContent);

        assertTrue(responseContent.contains("Email Already Exists for another user"));
    }

    @Test
    void testGetUserByNonExistentId() throws Exception {
        Long nonExistentUserId = 999L;

        MvcResult result = mockMvc.perform(get("/api/users/" + nonExistentUserId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Resource Not Found Error Response: " + responseContent);

        assertTrue(responseContent.contains("User not found with id : '999'"));
    }
    
    @Test
    void testGetAllUsers_HasData() throws Exception {
        userRepository.save(new User(null, "Alice", "Brown", "alicebrown@example.com"));
        userRepository.save(new User(null, "Bob", "Smith", "bobsmith@example.com"));

        MvcResult result = mockMvc.perform(get("/api/users")
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Get All Users Response: " + responseContent);

        assertTrue(responseContent.contains("\"firstName\":\"Alice\""));
        assertTrue(responseContent.contains("\"firstName\":\"Bob\""));
    }
    
    @Test
    void testGetUserById_NotFound() throws Exception {
        Long nonExistentUserId = 999L;

        MvcResult result = mockMvc.perform(get("/api/users/" + nonExistentUserId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Resource Not Found Error Response: " + responseContent);

        assertTrue(responseContent.contains("User not found with id : '999'"));
    }
    
    @Test
    void testDeleteUser_NotFound() throws Exception {
        Long nonExistentUserId = 888L;

        MvcResult result = mockMvc.perform(delete("/api/users/" + nonExistentUserId)
                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andReturn();

        String responseContent = result.getResponse().getContentAsString();
        System.out.println("Delete User Error Response: " + responseContent);

        assertTrue(responseContent.contains("User not found with id : '888'"));
    }
}