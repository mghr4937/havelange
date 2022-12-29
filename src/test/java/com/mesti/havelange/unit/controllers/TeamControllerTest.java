package com.mesti.havelange.unit.controllers;

import com.mesti.havelange.controllers.TeamController;
import com.mesti.havelange.controllers.dto.TeamDTO;
import com.mesti.havelange.services.TeamService;
import com.mesti.havelange.services.mapper.EntityDtoMapper;
import com.mesti.havelange.utils.TestUtils;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import javax.persistence.EntityNotFoundException;

import static org.assertj.core.api.Assertions.assertThat;
import static com.mesti.havelange.utils.TestUtils.*;

import java.util.List;

import static org.mockito.Mockito.when;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class TeamControllerTest {
    @Mock
    TeamService teamService;

    @InjectMocks
    TeamController teamController;

    @Test
    public void testGetAll_shouldReturnListOfTeamDTO() {
        // Given
        var teams = createRandomTeams(3);
        var expectedTeamDtos = EntityDtoMapper.mapAll(teams, TeamDTO.class);
        when(teamService.getAll()).thenReturn(expectedTeamDtos);

        // When
        ResponseEntity<List<TeamDTO>> response = teamController.getAll();

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedTeamDtos);
    }

    @Test
    public void testGetById_shouldReturnTeamDTO() {
        // Given
        var team = TestUtils.createRandomTeam();
        TeamDTO expectedTeamDto = EntityDtoMapper.map(team, TeamDTO.class);
        when(teamService.getByID(ID)).thenReturn(expectedTeamDto);

        // When
        ResponseEntity<TeamDTO> response = teamController.getById(ID);

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedTeamDto);
    }

    @Test
    public void testGetById_shouldThrowEntityNotFoundException() {
        // Given
        when(teamService.getByID(ID)).thenThrow(EntityNotFoundException.class);

        try {
            // When
            var response = teamController.getById(ID);
            // Then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            //assertThat(response.getBody()).is();
        } catch (EntityNotFoundException e) {}
    }

    @Test
    public void testGetByName_shouldReturnTeamDTO() {
        // Given
        var team = TestUtils.createRandomTeam();
        TeamDTO expectedTeamDto = EntityDtoMapper.map(team, TeamDTO.class);

        when(teamService.getByName(team.getName())).thenReturn(expectedTeamDto);

        // When
        ResponseEntity<TeamDTO> response = teamController.getByName(team.getName());

        // Then
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(response.getBody()).isEqualTo(expectedTeamDto);
    }

    @Test
    public void testGetByName_shouldThrowEntityNotFoundException() {
        // Given
        String name = "Test Team FC";
        when(teamService.getByName(name)).thenThrow(EntityNotFoundException.class);

        try {
            // When
            ResponseEntity<TeamDTO> response = teamController.getByName(name);

            // Then
            assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NOT_FOUND);
            assertThat(response.getBody()).isNull();
        } catch (EntityNotFoundException e) {}
    }

}
