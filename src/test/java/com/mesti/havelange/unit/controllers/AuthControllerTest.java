package com.mesti.havelange.unit.controllers;

import com.mesti.havelange.repositories.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.jdbc.EmbeddedDatabaseConnection;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.util.UriComponentsBuilder;

import static com.mesti.havelange.utils.TestUtils.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@Slf4j
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
        log.info("Test data loaded: {}", user);

        var url = UriComponentsBuilder.fromPath("/auth")
                .queryParam("username", TEST_USER)
                .queryParam("password", PWD)
                .toUriString();

        // Act - Realizamos la peticion POST a la URL "/auth"
        log.info("Performing POST {}", url);
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").exists())
                .andExpect(jsonPath("$.username").value(TEST_USER));
        log.info("POST Successful, User Logged");
    }

    @Test
    void authUserNotFoundFailure() throws Exception {
        var url = UriComponentsBuilder.fromPath("/auth")
                .queryParam("username", OTHER_USER)
                .queryParam("password", PWD)
                .toUriString();

        // Act - Realizamos la peticion POST a la URL "/auth"
        log.info("Performing POST {}", url);
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists());
        log.info("POST Successful, User not found.");
    }

    @Test
    void authPasswordFailure() throws Exception {
        // Arrange
        var user = getTestUser();
        user = userRepository.saveAndFlush(user);
        log.info("Test data loaded: {}", user);

        var url = UriComponentsBuilder.fromPath("/auth")
                .queryParam("username", TEST_USER)
                .queryParam("password", OTHER_USER)
                .toUriString();

        // Act - Realizamos la peticion POST a la URL "/auth"
        log.info("Performing POST {}", url);
        mockMvc.perform(MockMvcRequestBuilders.post(url)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").exists());
        log.info("POST Successful, Bad password.");

    }


}
