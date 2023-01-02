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
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.UriComponentsBuilder;

import static com.mesti.havelange.utils.TestUtils.*;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class UserControllerTest {

    private static final String TEST_NAME = "testname";
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserRepository userRepository;


    @Test
    @Transactional
    public void getAllUsers_shouldReturnListOfUsers() throws Exception {
        var user = userRepository.saveAndFlush(getUser(OTHER_USER));
        log.info("Test data loaded: {}", user);

        var url = UriComponentsBuilder.fromPath("/user").build().toUriString();

        // Act - Realizamos la peticion GET a la URL "/user/{id}"
        log.info("Performing GET {}", url);
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(2)));
        log.info("GET Successful, Users Listed");
    }

    @Test
    @Transactional
    public void getUser_shouldReturnUser() throws Exception {
        var user = userRepository.saveAndFlush(getUser(TEST_NAME));
        log.info("Test data loaded: {}", user);

        var url = UriComponentsBuilder.fromPath("/user/{id}")
                .buildAndExpand(user.getId())
                .toUriString();

        // Act - Realizamos la peticion GET a la URL "/user/{id}"
        log.info("Performing GET {}", url);
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(TEST_NAME));
    }

    @Test
    @Transactional
    public void getUser_shouldThrowEntityNotFoundException() throws Exception {
        var url = UriComponentsBuilder.fromPath("/user/{id}")
                .buildAndExpand(FAKER.number().randomNumber())
                .toUriString();

        // Act - Realizamos la peticion GET a la URL "/user/{id}"
        log.info("Performing GET {}", url);
        mockMvc.perform(get(url))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void getUser_shouldReturnUserByUsername() throws Exception {
        var user = userRepository.saveAndFlush(getUser(TEST_NAME));
        log.info("Test data loaded: {}", user);

        var url = UriComponentsBuilder.fromPath("/user/search")
                .queryParam("username", TEST_NAME)
                .build()
                .toUriString();

        // Act - Realizamos la peticion GET a la URL "/user/search?username="
        log.info("Performing GET {}", url);
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.username").value(TEST_NAME));
    }

    @Test
    @Transactional
    public void testGetByName_shouldThrowEntityNotFoundException() throws Exception {
        // Given
        String name = "JUAN-ALBERTO";
        var url = UriComponentsBuilder.fromPath("/user/search")
                .queryParam("username", name)
                .build()
                .toUriString();

        // Act - Realizamos la peticion GET a la URL "/user/search?username="
        log.info("Performing GET {}", url);

        // When
        mockMvc.perform(get(url))
                .andExpect(status().isNotFound());
    }

}