package com.mesti.havelange.unit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mesti.havelange.controllers.dto.PlayerDTO;
import com.mesti.havelange.models.Team;
import com.mesti.havelange.repositories.PlayerRepository;
import com.mesti.havelange.repositories.TeamRepository;
import com.mesti.havelange.services.mapper.EntityDtoMapper;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.BeforeEach;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class PlayerControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private PlayerRepository playerRepository;
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ObjectMapper objectMapper;

    private Team team;

    @BeforeEach
    private void setup() {
        team = teamRepository.saveAndFlush(createRandomTeam());
        log.info("Test data loaded: {}", team);

    }

    @Test
    @Transactional
    public void testGetAll_shouldReturnListOfPlayerDTO() throws Exception {
        // Given
        var players = createRandomPlayers(team, 7);
        playerRepository.saveAllAndFlush(players);
        log.info("Test data loaded: {}", players);

        var url = UriComponentsBuilder.fromPath("/player").build().toUriString();

        // Act - Realizamos la peticion GET a la URL "/player"
        log.info("Performing GET {}", url);
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(7)));
        log.info("GET Successful, Teams Listed");

    }

    @Test
    @Transactional
    public void testGetById_shouldReturnPlayerDTO() throws Exception {
        // Given
        var player = createRandomPlayer(team);
        player = playerRepository.saveAndFlush(player);
        var url = UriComponentsBuilder.fromPath("/player/{id}")
                .buildAndExpand(player.getId())
                .toUriString();

        // Act - Realizamos la peticion GET a la URL "/player/{id}"
        log.info("Performing GET {}", url);
        mockMvc.perform(get(url))
                .andExpect(jsonPath("$.id").value(player.getId().intValue()))
                .andExpect(jsonPath("$.name").value(player.getName()))
                .andExpect(jsonPath("$.lastName").value(player.getLastName()))
                .andExpect(jsonPath("$.shirtNumber").value(player.getShirtNumber()))
                .andExpect(jsonPath("$.enabled").value(player.isEnabled()))
                .andExpect(jsonPath("$.gender").value(player.getGender()))
                .andExpect(jsonPath("$.identityId").value(player.getIdentityId()))
                .andExpect(jsonPath("$.team.id").value(player.getTeam().getId().intValue()))
                .andExpect(jsonPath("$.team.name").value(player.getTeam().getName()));

        log.info("GET Successful");
    }

    @Test
    @Transactional
    public void testCreate_success() throws Exception {
        // Given
        var playerDTO = EntityDtoMapper.map(createRandomPlayer(team), PlayerDTO.class);

        // When
        mockMvc.perform(post("/player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(playerDTO.getName()))
                .andExpect(jsonPath("$.lastName").value(playerDTO.getLastName()))
                .andExpect(jsonPath("$.shirtNumber").value(playerDTO.getShirtNumber()))
                .andExpect(jsonPath("$.team.id").isNumber())
                .andExpect(jsonPath("$.enabled").value(playerDTO.isEnabled()))
                .andExpect(jsonPath("$.gender").value(playerDTO.getGender()))
                .andExpect(jsonPath("$.identityId").value(playerDTO.getIdentityId()));
    }

    @Test
    @Transactional
    public void testCreate_failure_uniqueConstraint() throws Exception {
        // Given
        var playerDTO = EntityDtoMapper.map(createRandomPlayer(team), PlayerDTO.class);

        // When
        mockMvc.perform(post("/player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(playerDTO.getName()))
                .andExpect(jsonPath("$.lastName").value(playerDTO.getLastName()))
                .andExpect(jsonPath("$.shirtNumber").value(playerDTO.getShirtNumber()))
                .andExpect(jsonPath("$.team.id").isNumber())
                .andExpect(jsonPath("$.enabled").value(playerDTO.isEnabled()))
                .andExpect(jsonPath("$.gender").value(playerDTO.getGender()))
                .andExpect(jsonPath("$.identityId").value(playerDTO.getIdentityId()));

        mockMvc.perform(post("/player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void testCreate_failure_emptyFields() throws Exception {
        // Given
        var playerDTO = EntityDtoMapper.map(createRandomTeam(), PlayerDTO.class);
        playerDTO.setName("");

        // When
        mockMvc.perform(post("/player")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerDTO)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void testUpdate_success() throws Exception {
        // Given
        var player = createRandomPlayer(team);
        player = playerRepository.saveAndFlush(player);
        log.info("Test data loaded: {}", player);

        var url = UriComponentsBuilder.fromPath("/player/{id}")
                .buildAndExpand(player.getId())
                .toUriString();

        var playerDTO = EntityDtoMapper.map(player, PlayerDTO.class);
        playerDTO.setName("Ronald Paolo");
        playerDTO.setShirtNumber(5);

        // When
        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(playerDTO.getName()))
                .andExpect(jsonPath("$.lastName").value(playerDTO.getLastName()))
                .andExpect(jsonPath("$.shirtNumber").value(playerDTO.getShirtNumber()))
                .andExpect(jsonPath("$.team.id").isNumber())
                .andExpect(jsonPath("$.enabled").value(playerDTO.isEnabled()))
                .andExpect(jsonPath("$.gender").value(playerDTO.getGender()))
                .andExpect(jsonPath("$.identityId").value(playerDTO.getIdentityId()));
    }

    @Test
    @Transactional
    public void testUpdate_failure_playerNotFound() throws Exception {
        // Given
        var player = createRandomPlayer(team);
        player = playerRepository.saveAndFlush(player);
        log.info("Test data loaded: {}", player);

        var url = UriComponentsBuilder.fromPath("/player/{id}")
                .buildAndExpand(FAKER.number().randomNumber())
                .toUriString();

        var playerDTO = EntityDtoMapper.map(player, PlayerDTO.class);
        playerDTO.setName("Ronald Paolo");
        playerDTO.setShirtNumber(5);

        // When
        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(playerDTO)))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void testGetById_shouldThrowEntityNotFoundException() throws Exception {
        var url = UriComponentsBuilder.fromPath("/player/{id}")
                .buildAndExpand(FAKER.number().randomNumber())
                .toUriString();

        // Act - Realizamos la peticion GET a la URL "/player/{id}"
        log.info("Performing GET {}", url);
        mockMvc.perform(get(url))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void testGetByTeamId_shouldThrowEntityNotFoundException() throws Exception {
        // Given

        var url = UriComponentsBuilder.fromPath("/player/search")
                .queryParam("teamId", FAKER.number().randomNumber())
                .build()
                .toUriString();

        // When
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().string("[]"));
    }


}