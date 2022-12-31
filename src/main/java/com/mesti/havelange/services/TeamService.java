package com.mesti.havelange.services;

import com.mesti.havelange.controllers.dto.PlayerDTO;
import com.mesti.havelange.controllers.dto.TeamDTO;
import com.mesti.havelange.models.Team;
import com.mesti.havelange.repositories.PlayerRepository;
import com.mesti.havelange.repositories.TeamRepository;
import com.mesti.havelange.services.mapper.EntityDtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
@Validated
public class TeamService {

    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;

    public TeamService(TeamRepository teamRepository, PlayerRepository playerRepository) {
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
    }

    public List<TeamDTO> getAll() {
        // obtén todos los equipos
        var teams = teamRepository.findAll();
        // recorre cada equipo y obtén los jugadores asociados
        for (var team : teams) {
            team.setPlayers(playerRepository.findByTeamId(team.getId()));
        }
        // mapea la lista de equipos a una lista de DTOs
        return EntityDtoMapper.mapAll(teams, TeamDTO.class);
    }

    public TeamDTO getByID(long id) {
        var team = teamRepository.getReferenceById(id);
        var teamDto = EntityDtoMapper.map(team, TeamDTO.class);
        if (teamDto.getPlayers() != null)
            teamDto.setPlayers(EntityDtoMapper.mapAll(team.getPlayers(), PlayerDTO.class));

        return teamDto;
    }

    public TeamDTO getByName(String name) {
        var team = teamRepository.findByName(name).orElseThrow(EntityNotFoundException::new);
        return EntityDtoMapper.map(team, TeamDTO.class);
    }

    public TeamDTO save(TeamDTO teamDTO) {
        var team = EntityDtoMapper.map(teamDTO, Team.class);
        team = teamRepository.save(team);
        return EntityDtoMapper.map(team, TeamDTO.class);
    }

    public TeamDTO update(long id, TeamDTO teamDTO) {
        var existingTeam = teamRepository.getReferenceById(id);

        // Actualizar los campos del equipo
        existingTeam.setName(teamDTO.getName());
        existingTeam.setShortname(teamDTO.getShortName());
        existingTeam.setCity(teamDTO.getCity());
        existingTeam.setPhone(teamDTO.getPhone());
        existingTeam.setEmail(teamDTO.getEmail());
        existingTeam.setClubColors(teamDTO.getClubColors());
        existingTeam.setEnabled(teamDTO.isEnabled());

        existingTeam = teamRepository.save(existingTeam);
        return EntityDtoMapper.map(existingTeam, TeamDTO.class);
    }

    public void disableTeam(Long id) {
        var team = teamRepository.getReferenceById(id);
        team.setEnabled(false);
        teamRepository.saveAndFlush(team);
    }
}
