package com.mesti.havelange.services;

import com.mesti.havelange.controllers.dto.PlayerDTO;
import com.mesti.havelange.controllers.dto.TeamDTO;
import com.mesti.havelange.models.Team;
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

    public TeamService(TeamRepository teamRepository) {
        this.teamRepository = teamRepository;
    }

    public List<TeamDTO> getAll() {
        var teams = teamRepository.findAll();
        return EntityDtoMapper.mapAll(teams, TeamDTO.class);
    }

    public TeamDTO getByID(long id) {
        var team = teamRepository.getReferenceById(id);
        var teamDto = EntityDtoMapper.map(team, TeamDTO.class);
        teamDto.setPlayers(EntityDtoMapper.mapAll(team.getPlayers(), PlayerDTO.class));
        return teamDto;
    }

    public TeamDTO getByName(String name) {
        var team = teamRepository.findByName(name).orElseThrow(EntityNotFoundException::new);
        return EntityDtoMapper.map(team, TeamDTO.class);
    }

    public TeamDTO save(TeamDTO teamDTO){
        var team = EntityDtoMapper.map(teamDTO, Team.class);
        team = teamRepository.save(team);
        return EntityDtoMapper.map(team, TeamDTO.class);
    }

    public TeamDTO update(long id, TeamDTO teamDTO){
        var existingTeam = teamRepository.getReferenceById(id);

        // Actualizar los campos del equipo
        existingTeam.setName(teamDTO.getName());
        existingTeam.setShortName(teamDTO.getShortName());
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
