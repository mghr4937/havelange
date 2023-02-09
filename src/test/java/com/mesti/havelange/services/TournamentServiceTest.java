package com.mesti.havelange.services;

import com.mesti.havelange.controllers.dto.LocationDTO;
import com.mesti.havelange.controllers.dto.tournament.TeamSummaryDTO;
import com.mesti.havelange.controllers.dto.tournament.TournamentDTO;
import com.mesti.havelange.models.*;
import com.mesti.havelange.repositories.LocationRepository;
import com.mesti.havelange.repositories.MatchRepository;
import com.mesti.havelange.repositories.TeamRepository;
import com.mesti.havelange.repositories.TournamentRepository;
import com.mesti.havelange.services.mapper.EntityDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class TournamentServiceTest {

    @Mock
    private TournamentRepository mockTournamentRepository;
    @Mock
    private LocationRepository mockLocationRepository;
    @Mock
    private TeamRepository mockTeamRepository;
    @Mock
    private MatchRepository mockMatchRepository;

    private TournamentService tournamentServiceUnderTest;

    @BeforeEach
    void setUp() {
        tournamentServiceUnderTest = new TournamentService(mockTournamentRepository, mockLocationRepository, mockTeamRepository,
                mockMatchRepository);
    }

    @Test
    void testGetAll() {
        // Setup
        final List<TournamentDTO> expectedResult = List.of(
                new TournamentDTO(0L, "name", "country", "city", List.of(new LocationDTO(0L, "name", "address", 0L)),
                        List.of(new TeamSummaryDTO(0L, "name", "city")), true));

        final var player = Player.builder().id(0L).name("name").lastName("lastName").dateOfBirth(LocalDate.of(2020, 1, 1))
                .shirtNumber(0).team(Team.builder().build()).enabled(true).gender("gender").identityId("identityId").build();

        var tournament = new Tournament(0L, "name", "country", "city", new ArrayList<>(),
                List.of(new Team(0L, "name", "shortname", "city", "phone", "email", "clubColors", true,
                        List.of(player))), true, List.of(Season.builder().build()));

        final var location = Location.builder().id(0L).name("name").address("address").tournament(tournament).build();
        tournament.getLocations().add(location);

        final List<Tournament> tournaments = List.of(tournament);
        when(mockTournamentRepository.findAll()).thenReturn(tournaments);

        // Run the test
        final List<TournamentDTO> result = tournamentServiceUnderTest.getAll();
        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAll_TournamentRepositoryReturnsNoItems() {
        // Setup
        when(mockTournamentRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<TournamentDTO> result = tournamentServiceUnderTest.getAll();
        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetById() {
        // Setup
        final TournamentDTO expectedResult = new TournamentDTO(0L, "name", "country", "city",
                List.of(new LocationDTO(0L, "name", "address", 0L)), List.of(new TeamSummaryDTO(0L, "name", "city")), true);

        final var player = Player.builder().id(0L).name("name").lastName("lastName").dateOfBirth(LocalDate.of(2020, 1, 1))
                .shirtNumber(0).team(Team.builder().build()).enabled(true).gender("gender").identityId("identityId").build();

        var tournament = new Tournament(0L, "name", "country", "city", new ArrayList<>(),
                List.of(new Team(0L, "name", "shortname", "city", "phone", "email", "clubColors", true,
                        List.of(player))), true, List.of(Season.builder().build()));

        final var location = Location.builder().id(0L).name("name").address("address").tournament(tournament).build();
        tournament.getLocations().add(location);

        when(mockTournamentRepository.getReferenceById(0L)).thenReturn(tournament);

        // Run the test
        final TournamentDTO result = tournamentServiceUnderTest.getById(0L);
        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testSaveTournament() {
        // Setup
        var expectedResult = new TournamentDTO(0L, "name", "country", "city",
                List.of(new LocationDTO(0L, "name", "address", 0L)), List.of(new TeamSummaryDTO(0L, "name", "city")), true);

        // Configure TournamentRepository.save(...).
        final var player = Player.builder().id(0L).name("name").lastName("lastName").dateOfBirth(LocalDate.of(2020, 1, 1))
                .shirtNumber(0).team(Team.builder().build()).enabled(true).gender("gender").identityId("identityId").build();

        var tournament = new Tournament(0L, "name", "country", "city", new ArrayList<>(),
                List.of(new Team(0L, "name", "shortname", "city", "phone", "email", "clubColors", true,
                        List.of(player))), true, List.of(Season.builder().build()));

        var location = Location.builder().id(0L).name("name").address("address").tournament(tournament).build();
        tournament.getLocations().add(location);

        when(mockTournamentRepository.save(any())).thenReturn(tournament);
        var tournamentDTO = EntityDtoMapper.map(tournament, TournamentDTO.class);

        // Run the test
        final TournamentDTO result = tournamentServiceUnderTest.save(tournamentDTO);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }



    @Test
    void testAddTeamsToTournament_TournamentRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockTournamentRepository.findById(0L)).thenReturn(Optional.empty());
        // Run the test
        assertThatThrownBy(() -> tournamentServiceUnderTest.addTeamsToTournament(0L, List.of(0L)))
                .isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void testRemoveTeamFromTournament_TournamentRepositoryFindByIdReturnsAbsent() {
        // Setup
        when(mockTournamentRepository.findById(0L)).thenReturn(Optional.empty());
        // Run the test
        assertThatThrownBy(() -> tournamentServiceUnderTest.removeTeamFromTournament(0L, 0L)).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void testRemoveTeamFromTournament_TeamRepositoryReturnsAbsent() {
        // Setup
        final var player = Player.builder().id(0L).name("name").lastName("lastName").dateOfBirth(LocalDate.of(2020, 1, 1))
                .shirtNumber(0).team(Team.builder().build()).enabled(true).gender("gender").identityId("identityId").build();

        var tournament = new Tournament(0L, "name", "country", "city", new ArrayList<>(),
                List.of(new Team(0L, "name", "shortname", "city", "phone", "email", "clubColors", true,
                        List.of(player))), true, List.of(Season.builder().build()));

        final var location = Location.builder().id(0L).name("name").address("address").tournament(tournament).build();
        tournament.getLocations().add(location);

        when(mockTournamentRepository.findById(0L)).thenReturn(Optional.of(tournament));

        when(mockTeamRepository.findById(0L)).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> tournamentServiceUnderTest.removeTeamFromTournament(0L, 0L)).isInstanceOf(EntityNotFoundException.class);

        // Verify that the repository methods were called exactly once
        verify(mockTournamentRepository, times(1)).findById(0L);
        verify(mockTeamRepository, times(1)).findById(0L);
        verifyNoMoreInteractions(mockTournamentRepository, mockTeamRepository);
    }



}
