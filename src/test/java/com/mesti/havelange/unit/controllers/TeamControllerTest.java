package com.mesti.havelange.unit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mesti.havelange.controllers.dto.TeamDTO;
import com.mesti.havelange.repositories.TeamRepository;
import com.mesti.havelange.services.mapper.EntityDtoMapper;
import com.mesti.havelange.utils.TestUtils;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    public void testGetAll_shouldReturnListOfTeamDTO() throws Exception {
        // Given
        var teams = createRandomTeams(3);
        teamRepository.saveAllAndFlush(teams);
        log.info("Test data loaded: {}", teams);

        var url = UriComponentsBuilder.fromPath("/team").build().toUriString();

        // Act - Realizamos la peticion GET a la URL "/team"
        log.info("Performing GET {}", url);
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)));
        log.info("GET Successful, Teams Listed");

    }

    @Test
    @Transactional
    public void testGetById_shouldReturnTeamDTO() throws Exception {
        // Given
        var team = teamRepository.saveAndFlush(TestUtils.createRandomTeam());
        log.info("Test data loaded: {}", team);

        var url = UriComponentsBuilder.fromPath("/team/{id}")
                .buildAndExpand(team.getId())
                .toUriString();

        // Act - Realizamos la peticion GET a la URL "/team/{id}"
        log.info("Performing GET {}", url);
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(team.getName()))
                .andExpect(jsonPath("$.city").value(team.getCity()));
        log.info("GET Successful");
    }

    @Test
    @Transactional
    public void testGetById_shouldThrowEntityNotFoundException() throws Exception {
        var url = UriComponentsBuilder.fromPath("/team/{id}")
                .buildAndExpand(FAKER.number().randomNumber())
                .toUriString();

        // Act - Realizamos la peticion GET a la URL "/team/{id}"
        log.info("Performing GET {}", url);
        mockMvc.perform(get(url))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void testGetByName_shouldReturnTeamDTO() throws Exception {
        // Given
        var team = TestUtils.createRandomTeam();
        teamRepository.saveAndFlush(team);
        log.info("Test data loaded: {}", team);

        var url = UriComponentsBuilder.fromPath("/team/search")
                .queryParam("name", team.getName())
                .build()
                .toUriString();

        // Act - Realizamos la peticion GET a la URL "/team/{id}"
        log.info("Performing GET {}", url);
        mockMvc.perform(get(url))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(team.getName()))
                .andExpect(jsonPath("$.city").value(team.getCity()));
    }

    @Test
    @Transactional
    public void testGetByName_shouldThrowEntityNotFoundException() throws Exception {
        // Given
        var name = "Test Team FC";

        var url = UriComponentsBuilder.fromPath("/team/search")
                .queryParam("name", name)
                .build()
                .toUriString();

        // When
        mockMvc.perform(get(url))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

    @Test
    @Transactional
    public void testCreate_success() throws Exception {
        // Given
        TeamDTO teamDto = EntityDtoMapper.map(createRandomTeam(), TeamDTO.class);

        // When
        mockMvc.perform(post("/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNotEmpty())
                .andExpect(jsonPath("$.name").value(teamDto.getName()))
                .andExpect(jsonPath("$.shortName").value(teamDto.getShortName()))
                .andExpect(jsonPath("$.city").value(teamDto.getCity()))
                .andExpect(jsonPath("$.phone").value(teamDto.getPhone()))
                .andExpect(jsonPath("$.email").value(teamDto.getEmail()))
                .andExpect(jsonPath("$.clubColors").value(teamDto.getClubColors()))
                .andExpect(jsonPath("$.enabled").value(teamDto.isEnabled()));
    }

    @Test
    @Transactional
    public void testCreate_failure_uniqueConstraint() throws Exception {
        // Given
        TeamDTO teamDto = EntityDtoMapper.map(createRandomTeam(), TeamDTO.class);

        // When
        mockMvc.perform(post("/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.name").value(teamDto.getName()))
                .andExpect(jsonPath("$.shortName").value(teamDto.getShortName()))
                .andExpect(jsonPath("$.city").value(teamDto.getCity()))
                .andExpect(jsonPath("$.phone").value(teamDto.getPhone()))
                .andExpect(jsonPath("$.email").value(teamDto.getEmail()))
                .andExpect(jsonPath("$.clubColors").value(teamDto.getClubColors()))
                .andExpect(jsonPath("$.enabled").value(teamDto.isEnabled()));

        mockMvc.perform(post("/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void testCreate_failure_emptyFields() throws Exception {
        // Given
        TeamDTO teamDto = EntityDtoMapper.map(createRandomTeam(), TeamDTO.class);
        teamDto.setName("");

        // When
        mockMvc.perform(post("/team")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamDto)))
                .andExpect(status().isBadRequest());
    }

    @Test
    @Transactional
    public void testUpdate_success() throws Exception {
        // Given
        var team = TestUtils.createRandomTeam();
        team = teamRepository.saveAndFlush(team);
        log.info("Test data loaded: {}", team);

        var url = UriComponentsBuilder.fromPath("/team/{id}")
                .buildAndExpand(team.getId())
                .toUriString();

        TeamDTO teamDto = EntityDtoMapper.map(team, TeamDTO.class);
        teamDto.setName("Liverpool FC");
        teamDto.setCity("Montevideo");

        // When
        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(teamDto.getName()))
                .andExpect(jsonPath("$.shortName").value(team.getShortname()))
                .andExpect(jsonPath("$.city").value(teamDto.getCity()))
                .andExpect(jsonPath("$.phone").value(team.getPhone()))
                .andExpect(jsonPath("$.email").value(team.getEmail()))
                .andExpect(jsonPath("$.clubColors").value(team.getClubColors()));

    }

    @Test
    @Transactional
    public void testUpdate_failure_teamNotFound() throws Exception {
        // Given
        var team = TestUtils.createRandomTeam();
        team = teamRepository.saveAndFlush(team);
        log.info("Test data loaded: {}", team);

        var url = UriComponentsBuilder.fromPath("/team/{id}")
                .buildAndExpand(FAKER.number().randomNumber())
                .toUriString();

        TeamDTO teamDto = EntityDtoMapper.map(team, TeamDTO.class);
        teamDto.setName("Liverpool FC");
        teamDto.setCity("Montevideo");

        // When
        mockMvc.perform(put(url)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(teamDto)))
                .andExpect(status().isNotFound());

    }
}
