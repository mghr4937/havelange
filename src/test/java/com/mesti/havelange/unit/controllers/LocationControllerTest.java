package com.mesti.havelange.unit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mesti.havelange.controllers.dto.LocationDTO;
import com.mesti.havelange.models.Location;
import com.mesti.havelange.repositories.LocationRepository;
import com.mesti.havelange.repositories.TournamentRepository;
import com.mesti.havelange.services.mapper.EntityDtoMapper;
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

import java.util.List;

import static com.mesti.havelange.utils.TestUtils.createRandomLocations;
import static com.mesti.havelange.utils.TestUtils.createRandomTournament;
import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class LocationControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private LocationRepository locationRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    public void testGetAll_shouldReturnListOfLocationDTO() throws Exception {
        // Given
        var tournament = createRandomTournament(1).get(0);
        tournament = tournamentRepository.saveAndFlush(tournament);
        List<Location> locations = createRandomLocations(tournament, 3);
        locations = locationRepository.saveAllAndFlush(locations);
        log.info("Data Loaded {}", locations);

        // Act - Realizamos la petición GET a la URL "/location"
        log.info("Performing GET /location");
        mockMvc.perform(get("/location"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder(
                        locations.get(0).getName(),
                        locations.get(1).getName(),
                        locations.get(2).getName()
                )))
                .andExpect(jsonPath("$[*].address", containsInAnyOrder(
                        locations.get(0).getAddress(),
                        locations.get(1).getAddress(),
                        locations.get(2).getAddress()
                )))
                .andExpect(jsonPath("$[*].tournamentId", containsInAnyOrder(
                        tournament.getId().intValue(),
                        tournament.getId().intValue(),
                        tournament.getId().intValue()
                )));
    }

    @Test
    @Transactional
    public void testCreate_success() throws Exception {
        // Given
        var tournament = createRandomTournament(1).get(0);
        tournament = tournamentRepository.saveAndFlush(tournament);
        log.info("Data Loaded {}", tournament);
        var locationDTO = EntityDtoMapper.map(createRandomLocations(tournament, 1).get(0), LocationDTO.class);

        mockMvc.perform(post("/location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(locationDTO.getName()))
                .andExpect(jsonPath("$.address").value(locationDTO.getAddress()))
                .andExpect(jsonPath("$.tournamentId").value(locationDTO.getTournamentId()));
    }

    @Test
    @Transactional
    public void testGetById_shouldReturnLocationDTO() throws Exception {
        // Given
        var tournament = createRandomTournament(1).get(0);
        tournament = tournamentRepository.saveAndFlush(tournament);
        log.info("Data Loaded {}", tournament);
        List<Location> locations = createRandomLocations(tournament, 1);
        tournament.setLocations(locations);
        locations = locationRepository.saveAllAndFlush(locations);
        log.info("Data Loaded {}", locations);

        // When
        mockMvc.perform(get("/location/{id}", locations.get(0).getId())
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.name").value(locations.get(0).getName()))
                .andExpect(jsonPath("$.address").value(locations.get(0).getAddress()))
                .andExpect(jsonPath("$.tournamentId").value(tournament.getId()));
    }

    @Test
    @Transactional
    public void testCreate_failure_emptyFields() throws Exception {
        // Given
        var tournament = createRandomTournament(1).get(0);
        tournament = tournamentRepository.saveAndFlush(tournament);
        log.info("Data Loaded {}", tournament);
        var locationDTO = EntityDtoMapper.map(createRandomLocations(tournament, 1).get(0), LocationDTO.class);

        // Setear algún campo a vacío
        locationDTO.setName("");
        locationDTO.setAddress("");

        mockMvc.perform(post("/location")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.errors").isArray())
                .andExpect(jsonPath("$.errors", hasSize(2)));
    }

    @Test
    @Transactional
    public void testUpdate_success() throws Exception {
        // Given
        var tournament = createRandomTournament(1).get(0);
        tournament = tournamentRepository.saveAndFlush(tournament);
        log.info("Data Loaded {}", tournament);
        var location = createRandomLocations(tournament, 1).get(0);
        location = locationRepository.saveAndFlush(location);
        log.info("Data Loaded {}", location);

        var locationDTO = EntityDtoMapper.map(location, LocationDTO.class);
        locationDTO.setName("Estadio Centenario");

        // When
        mockMvc.perform(put("/location/{id}", location.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(location.getId()))
                .andExpect(jsonPath("$.name").value(locationDTO.getName()))
                .andExpect(jsonPath("$.address").value(locationDTO.getAddress()))
                .andExpect(jsonPath("$.tournamentId").value(locationDTO.getTournamentId()));

        // Then
        location = locationRepository.getReferenceById(location.getId());
        assertEquals(locationDTO.getName(), location.getName());
        assertEquals(locationDTO.getAddress(), location.getAddress());
        assertEquals(locationDTO.getTournamentId(), location.getTournament().getId());
    }

    @Test
    @Transactional
    public void testUpdate_failure_locationNotFound() throws Exception {
        var tournament = createRandomTournament(1).get(0);
        tournament = tournamentRepository.saveAndFlush(tournament);
        log.info("Data Loaded {}", tournament);
        // Given
        long nonExistentLocationId = 999L;
        var locationDTO = EntityDtoMapper.map(createRandomLocations(tournament, 1).get(0), LocationDTO.class);

        // When
        mockMvc.perform(put("/location/{id}", nonExistentLocationId)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(locationDTO)))
                // Then
                .andExpect(status().isNotFound());
    }
}
