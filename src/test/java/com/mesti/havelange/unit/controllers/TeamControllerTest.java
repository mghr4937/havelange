package com.mesti.havelange.unit.controllers;

import com.mesti.havelange.repositories.TeamRepository;
import com.mesti.havelange.utils.TestUtils;
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
public class TeamControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TeamRepository teamRepository;

    @Test
    @Transactional
    public void testGetAll_shouldReturnListOfTeamDTO() throws Exception {
        // Given
        var teams = createRandomTeams(3);
        teamRepository.saveAllAndFlush(teams);

        // When
        mockMvc.perform(get("/team"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(4)));

    }

    @Test
    @Transactional
    public void testGetById_shouldReturnTeamDTO() throws Exception {
        // Given
        var team = teamRepository.saveAndFlush(TestUtils.createRandomTeam());

        mockMvc.perform(get("/team/{id}", team.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(team.getName()))
                .andExpect(jsonPath("$.city").value(team.getCity()));
    }

    @Test
    @Transactional
    public void testGetById_shouldThrowEntityNotFoundException() throws Exception {

        mockMvc.perform(get("/teams/{id}", 55))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void testGetByName_shouldReturnTeamDTO() throws Exception {
        // Given
        var team = TestUtils.createRandomTeam();
        teamRepository.saveAndFlush(team);

        mockMvc.perform(get("/team/search?name=" + team.getName()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value(team.getName()))
                .andExpect(jsonPath("$.city").value(team.getCity()));
    }

    @Test
    @Transactional
    public void testGetByName_shouldThrowEntityNotFoundException() throws Exception {
        // Given
        String name = "Test Team FC";

        // When
        mockMvc.perform(get("/team/search?name=" + name))
                .andExpect(status().isNotFound())
                .andExpect(content().string(""));
    }

}
