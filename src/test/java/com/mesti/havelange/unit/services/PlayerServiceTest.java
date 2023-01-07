package com.mesti.havelange.unit.services;

import com.mesti.havelange.controllers.dto.PlayerDTO;
import com.mesti.havelange.models.Player;
import com.mesti.havelange.repositories.PlayerRepository;
import com.mesti.havelange.repositories.TeamRepository;
import com.mesti.havelange.services.PlayerService;
import com.mesti.havelange.services.mapper.EntityDtoMapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.List;

import static com.mesti.havelange.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PlayerServiceTest {

    @Mock
    private PlayerRepository playerRepository;

    @Mock
    private TeamRepository teamRepository;

    @InjectMocks
    private PlayerService playerService;

    @Test
    public void testGetAll_shouldReturnListOfPlayerDTO() {
        // Arrange
        List<Player> players = createRandomTeamWithPlayers(3).getPlayers();
        when(playerRepository.findAll()).thenReturn(players);

        List<PlayerDTO> expectedPlayers = EntityDtoMapper.mapAll(players, PlayerDTO.class);

        // Act
        List<PlayerDTO> actualPlayers = playerService.getAll();

        // Assert
        assertThat(actualPlayers).isEqualTo(expectedPlayers);
    }

    @Test
    public void testGetByID_shouldReturnPlayerDTO() {
        var player = createRandomPlayer();
        player.setId(1L);
        // when
        when(playerRepository.getReferenceById(player.getId())).thenReturn(player);

        PlayerDTO playerDTO = playerService.getById(player.getId());

        // then
        Assertions.assertNotNull(playerDTO);
        assertEquals(player.getId(), playerDTO.getId());
        assertEquals(player.getName(), playerDTO.getName());
        assertEquals(player.getDateOfBirth(), playerDTO.getDateOfBirth());
        assertEquals(player.getShirtNumber(), playerDTO.getShirtNumber());
        assertEquals(player.isEnabled(), playerDTO.isEnabled());
        assertEquals(player.getIdentityId(), playerDTO.getIdentityId());
        assertEquals(player.getTeam().getId(), playerDTO.getTeam().getId());
    }

    @Test
    public void testGetByID_shouldThrowEntityNotFoundExceptionIfPlayerDoesNotExist() {
        // given
        Long playerId = 1L;
        when(playerRepository.getReferenceById(playerId)).thenThrow(EntityNotFoundException.class);

        // when
        Throwable thrown = catchThrowable(() -> playerService.getById(playerId));

        // then
        assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
        verify(playerRepository).getReferenceById(playerId);
        verify(playerRepository, never()).save(any());

    }

    @Test
    public void testSave_shouldSavePlayer() {
        var player = createRandomPlayer();
        player.setId(1L);
        var team = player.getTeam();
        team.setId(1L);
        var playerDto = EntityDtoMapper.map(player, PlayerDTO.class);

        // when
        when(teamRepository.getReferenceById(team.getId())).thenReturn(team);
        when(playerRepository.save(player)).thenReturn(player);

        var savedPlayer = playerService.save(playerDto);

        // then
        verify(teamRepository).getReferenceById(team.getId());
        verify(playerRepository).save(player);

        assertEquals(playerDto.getName(), savedPlayer.getName());
        assertEquals(playerDto.getDateOfBirth(), savedPlayer.getDateOfBirth());
        assertEquals(playerDto.getShirtNumber(), savedPlayer.getShirtNumber());
        assertEquals(playerDto.isEnabled(), savedPlayer.isEnabled());
        assertEquals(playerDto.getIdentityId(), savedPlayer.getIdentityId());
        assertEquals(playerDto.getTeam().getId(), savedPlayer.getTeam().getId());
    }

    @Test
    public void testSave_shouldThrowEntityNotFoundExceptionIfATeamDoesNotExist() {
        // given
        var player = createRandomPlayer();
        player.setId(1L);
        var team = player.getTeam();
        team.setId(1L);
        var playerDto = EntityDtoMapper.map(player, PlayerDTO.class);
        when(teamRepository.getReferenceById(team.getId())).thenThrow(EntityNotFoundException.class);

        // when
        Throwable thrown = catchThrowable(() -> playerService.save(playerDto));

        // then
        assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
        verify(teamRepository).getReferenceById(team.getId());
        verify(playerRepository, never()).save(any());

    }


    @Test
    public void testDisablePlayer_shouldSetEnabledToFalse() {
        // given
        var player = createRandomPlayer();
        player.setId(1L);

        when(playerRepository.getReferenceById(player.getId())).thenReturn(player);
        when(playerRepository.save(player)).thenReturn(player);

        // when
        playerService.disablePlayer(player.getId());

        // then
        assertFalse(player.isEnabled());
        verify(playerRepository).getReferenceById(player.getId());
        verify(playerRepository).save(player);
    }

    @Test
    public void testDisablePlayer_shouldThrowEntityNotFoundExceptionIfPlayerDoesNotExist() {
        // given
        var playerId = 1L;
        when(playerRepository.getReferenceById(playerId)).thenThrow(EntityNotFoundException.class);

        // when
        Throwable thrown = catchThrowable(() -> playerService.disablePlayer(playerId));

        // then
        assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
        verify(playerRepository).getReferenceById(playerId);
        verify(playerRepository, never()).save(any());

    }


}
