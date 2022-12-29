package com.mesti.havelange.services;

import com.mesti.havelange.controllers.dto.TeamDTO;
import com.mesti.havelange.repositories.TeamRepository;
import com.mesti.havelange.services.mapper.EntityDtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
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
        var team = teamRepository.findById(id).orElseThrow();
        return EntityDtoMapper.map(team, TeamDTO.class);

    }

    public TeamDTO getByName(String name) {
        var team = teamRepository.findByName(name).orElseThrow();
        return EntityDtoMapper.map(team, TeamDTO.class);
    }

    public void delete(Long id) {
        var team = teamRepository.findById(id).orElseThrow();
        teamRepository.delete(team);
    }
}
