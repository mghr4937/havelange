package com.mesti.havelange.services;


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
public class PlayerService {

    private final PlayerRepository playerRepository;
    private final TeamRepository teamRepository;

    public PlayerService(PlayerRepository playerRepository, TeamRepository teamRepository) {
        this.playerRepository = playerRepository;
        this.teamRepository = teamRepository;
    }

    public List<PlayerDTO> getAll() {
        var players = playerRepository.findAll();
        return EntityDtoMapper.mapAll(players, PlayerDTO.class);
    }

    public PlayerDTO getById(long id) {
        var player = playerRepository.getReferenceById(id);
        return EntityDtoMapper.map(player, PlayerDTO.class);
    }

    public PlayerDTO save(PlayerDTO playerDTO) {
        Team team = teamRepository.getReferenceById(playerDTO.getId());
        var player = EntityDtoMapper.map(playerDTO, Player.class);

        player.setTeam(team);
        player = playerRepository.save(player);
        return EntityDtoMapper.map(player, PlayerDTO.class);
    }

    public void disablePlayer(Long id) {
        var player = playerRepository.getReferenceById(id);
        player.setEnabled(false);
        playerRepository.saveAndFlush(player);
    }

}
