package com.mesti.havelange.unit.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mesti.havelange.repositories.TournamentRepository;
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

import static com.mesti.havelange.utils.TestUtils.createRandomTournament;
import static org.hamcrest.Matchers.containsInAnyOrder;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@Slf4j
@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
@AutoConfigureTestDatabase(connection = EmbeddedDatabaseConnection.H2)
public class TournamentControllerTest {
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TournamentRepository tournamentRepository;
    @Autowired
    private ObjectMapper objectMapper;

    @Test
    @Transactional
    public void testGetAll_shouldReturnListOfTournamentDTO() throws Exception {
        // Given
        var tournaments =createRandomTournament(3);
        tournaments = tournamentRepository.saveAllAndFlush(tournaments);
        log.info("Data Loaded {}", tournaments);

        // Act - Realizamos la petición GET a la URL "/tournament"
        log.info("Performing GET /tournament");
        mockMvc.perform(get("/tournament"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$", hasSize(3)))
                .andExpect(jsonPath("$[*].name", containsInAnyOrder(
                        tournaments.get(0).getName(),
                        tournaments.get(1).getName(),
                        tournaments.get(2).getName()
                )))
                .andExpect(jsonPath("$[*].city", containsInAnyOrder(
                        tournaments.get(0).getCity(),
                        tournaments.get(1).getCity(),
                        tournaments.get(2).getCity()
                )));

    }

}
