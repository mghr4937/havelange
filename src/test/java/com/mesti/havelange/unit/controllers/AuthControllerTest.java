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

import static com.mesti.havelange.utils.TestUtils.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class AuthControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;

    @Test
    void authSuccess() throws Exception {
        // Arrange
        var user = getTestUser();
        userRepository.saveAndFlush(user);

        // Act
        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username", TEST_USER)
                        .param("password", PWD))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.username").value(TEST_USER));
    }

    @Test
    void authUserFailure() throws Exception {
        // Act
        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username", TEST_USER)
                        .param("password", PWD))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists());

    }

    @Test
    void authPasswordFailure() throws Exception {
        // Arrange
        var user = getTestUser();
        userRepository.saveAndFlush(user);

        // Act
        mockMvc.perform(post("/auth")
                        .contentType(MediaType.APPLICATION_JSON)
                        .param("username", TEST_USER)
                        .param("password", OTHER_USER))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists());

    }


}
