package com.mesti.havelange.unit.controllers;

import com.mesti.havelange.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static com.mesti.havelange.utils.TestUtils.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserControllerTest {

    private static final String ADMIN = "admin";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;


    @Test
    @Transactional
    public void getAllUsers_shouldReturnListOfUsers() throws Exception {
        userRepository.saveAndFlush(getUser(OTHER_USER));
        mockMvc.perform(get("/user"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    @Transactional
    public void getUser_shouldReturnUser() throws Exception {
        mockMvc.perform(get("/user/{id}", ID))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(ADMIN));
    }

    @Test
    @Transactional
    public void getUser_shouldThrowEntityNotFoundException() throws Exception {

        mockMvc.perform(get("/teams/{id}", 55))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void getUser_shouldReturnUserByUsername() throws Exception {
        mockMvc.perform(get("/user/search?username=" + ADMIN))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(ADMIN));
    }

    @Test
    @Transactional
    public void testGetByName_shouldThrowEntityNotFoundException() throws Exception {
        // Given
        String name = "JUAN-ALBERTO";

        // When
        mockMvc.perform(get("/user/search?username=" + name))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

}