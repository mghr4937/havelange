package com.mesti.havelange.unit.services;

import com.mesti.havelange.utils.TestUtils;
import com.mesti.havelange.controllers.dto.TeamDTO;
import com.mesti.havelange.models.Team;
import com.mesti.havelange.repositories.TeamRepository;
import com.mesti.havelange.services.TeamService;
import com.mesti.havelange.services.mapper.EntityDtoMapper;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.function.Executable;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

import static com.mesti.havelange.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class TeamServiceTest {

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private TeamService teamService;

    @Test
    public void testGetAll_shouldReturnListOfTeamDTO() {
        // Given
        List<Team> teams = TestUtils.createRandomTeams(5);
        when(teamRepository.findAll()).thenReturn(teams);

        List<TeamDTO> expectedTeamDtos = EntityDtoMapper.mapAll(teams, TeamDTO.class);

        // When
        List<TeamDTO> teamDtos = teamService.getAll();

        // Then
        assertThat(teamDtos).isEqualTo(expectedTeamDtos);
    }

    @Test
    public void testGetByID_shouldReturnTeamDTO() {
        // Given
        Team team = TestUtils.createRandomTeam();
        when(teamRepository.findById(ID)).thenReturn(Optional.of(team));
        TeamDTO expectedTeamDto = EntityDtoMapper.map(team, TeamDTO.class);

        // When
        TeamDTO teamDto = teamService.getByID(ID);

        // Then
        assertThat(teamDto).isEqualTo(expectedTeamDto);
    }

    @Test
    public void testGetByID_shouldThrowNoSuchElementException() {
        // Given
        when(teamRepository.findById(ID)).thenReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> teamService.getByID(ID));

        // Then
        assertThat(thrown).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testGetByName_shouldReturnTeamDTO() {
        // Given
        var team = TestUtils.createRandomTeam();
        var name = team.getName();
        when(teamRepository.findByName(name)).thenReturn(Optional.of(team));
        var expectedTeamDto = EntityDtoMapper.map(team, TeamDTO.class);

        // When
        var teamDto = teamService.getByName(name);

        // Then
        assertThat(teamDto).isEqualTo(expectedTeamDto);
    }

    @Test
    public void testGetByName_shouldThrowExceptionWhenTeamNotFound() {
        // Given
        var name = TestUtils.FAKER.team().name();
        when(teamRepository.findByName(name)).thenReturn(Optional.empty());

        // When
        Throwable thrown = catchThrowable(() -> teamService.getByName(name));

        // Then
        assertThat(thrown).isInstanceOf(NoSuchElementException.class);
    }

    @Test
    public void testDelete_whenTeamExists_shouldDeleteTeam() {
        // Given
        var team = TestUtils.createRandomTeam();
        when(teamRepository.findById(team.getId())).thenReturn(Optional.of(team));

        // When
        teamService.delete(team.getId());

        // Then
        verify(teamRepository).delete(team);
    }

    @Test
    public void testDelete_whenTeamDoesNotExist_shouldThrowException() {
        // Given
        when(teamRepository.findById(ID)).thenReturn(Optional.empty());

        // When
        Executable deleteAction = () -> teamService.delete(ID);

        // Then
        assertThrows(NoSuchElementException.class, deleteAction);
    }


}
