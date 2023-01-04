package com.mesti.havelange.services;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import com.mesti.havelange.controllers.dto.PlayerDTO;
import com.mesti.havelange.models.Player;
import com.mesti.havelange.models.Team;
import com.mesti.havelange.repositories.TeamRepository;
import com.mesti.havelange.repositories.PlayerRepository;
import com.mesti.havelange.services.mapper.EntityDtoMapper;

import java.util.List;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public List<PlayerDTO> getAll() {
        var players = playerRepository.findAll();
        return EntityDtoMapper.mapAll(players, PlayerDTO.class);
    }

    public PlayerDTO getById(long id) {
        var player = playerRepository.getReferenceById(id);
        return EntityDtoMapper.map(player, PlayerDTO.class);
    }

    public List<PlayerDTO> getByTeamId(long id) {
        var players = playerRepository.findByTeamId(id);
        return EntityDtoMapper.mapAll(players, PlayerDTO.class);
    }

    public PlayerDTO save(PlayerDTO playerDTO) {
        Team team = teamRepository.getReferenceById(playerDTO.getTeam().getId());
        var player = EntityDtoMapper.map(playerDTO, Player.class);

        player.setTeam(team);
        player = playerRepository.save(player);
        return EntityDtoMapper.map(player, PlayerDTO.class);
    }

    public PlayerDTO update(long id, PlayerDTO playerDTO) {
        var existingPlayer = playerRepository.getReferenceById(id);
        var team = teamRepository.getReferenceById(playerDTO.getTeam().getId());

        // Actualizar los campos del jugador
        existingPlayer.setName(playerDTO.getName());
        existingPlayer.setLastName(playerDTO.getLastName());
        existingPlayer.setDateOfBirth(playerDTO.getDateOfBirth());
        existingPlayer.setShirtNumber(playerDTO.getShirtNumber());
        existingPlayer.setTeam(team);
        existingPlayer.setEnabled(playerDTO.isEnabled());
        existingPlayer.setGender(playerDTO.getGender());
        existingPlayer.setIdentityId(playerDTO.getIdentityId());

        existingPlayer = playerRepository.save(existingPlayer);
        return EntityDtoMapper.map(existingPlayer, PlayerDTO.class);
    }

    public void disablePlayer(Long id) {
        var player = playerRepository.getReferenceById(id);
        player.setEnabled(false);
        playerRepository.save(player);
    }
}
