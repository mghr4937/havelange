package com.mesti.havelange.unit.controllers;

import com.mesti.havelange.models.Match;
import com.mesti.havelange.models.Season;
import com.mesti.havelange.models.Team;
import com.mesti.havelange.models.Tournament;
import com.mesti.havelange.repositories.MatchDayRepository;
import com.mesti.havelange.repositories.MatchRepository;
import com.mesti.havelange.repositories.SeasonRepository;
import com.mesti.havelange.services.FixtureService;
import com.mesti.havelange.utils.TestUtils;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class FixtureServiceTest {
    private final SeasonRepository seasonRepository = mock(SeasonRepository.class);
    private final MatchRepository matchRepository = mock(MatchRepository.class);

    private  final MatchDayRepository matchDayRepository = mock(MatchDayRepository.class);

    private final FixtureService fixtureService = new FixtureService(seasonRepository, matchRepository, matchDayRepository);

    @Test
    void generateFixture_notEnoughTeams_shouldThrowException() {
        Season season = Season.builder().tournament(Tournament.builder().teams(new ArrayList<>()).build()).build();
        when(seasonRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(season));

        assertThrows(IllegalArgumentException.class, () -> fixtureService.generateLigueFixture(1L, LocalDateTime.now(), 1, false));
    }

    @Test
    void generateFixture_enoughTeams_shouldSave1Match() {
        List<Team> teams = List.of(
                TestUtils.createRandomTeam(),
                TestUtils.createRandomTeam()
        );
        Season season = Season.builder().id(1L).startDate(LocalDate.now()).tournament(Tournament.builder().teams(teams).build()).build();
        when(seasonRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(season));

        fixtureService.generateLigueFixture(1L, LocalDateTime.now(), 1, false);

        verify(seasonRepository).findById(1L);
        verify(matchRepository, times(1)).save(any(Match.class));

    }

    @Test
    void generateFixture_enoughTeams_shouldSave2Matches() {
        List<Team> teams = List.of(
                TestUtils.createRandomTeam(),
                TestUtils.createRandomTeam()
        );
        Season season = Season.builder().id(1L).startDate(LocalDate.now()).tournament(Tournament.builder().teams(teams).build()).build();
        when(seasonRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(season));

        fixtureService.generateLigueFixture(1L, LocalDateTime.now(), 1, true);

        verify(seasonRepository).findById(1L);
        verify(matchRepository, times(2)).save(any(Match.class));
    }
    @Test
    void generateFixture_enoughTeams_shouldSave5Matches() {
        List<Team> teams = List.of(
                TestUtils.createRandomTeam(),
                TestUtils.createRandomTeam(),
                TestUtils.createRandomTeam(),
                TestUtils.createRandomTeam(),
                TestUtils.createRandomTeam()
        );
        Season season = Season.builder().id(1L).startDate(LocalDate.now()).tournament(Tournament.builder().teams(teams).build()).build();
        when(seasonRepository.findById(any(Long.class))).thenReturn(java.util.Optional.of(season));

        fixtureService.generateLigueFixture(1L, LocalDateTime.now(), 1, false);

        verify(seasonRepository).findById(1L);
        verify(matchRepository, times(10)).save(any(Match.class));
    }



}
