package com.mesti.havelange.service;

import com.mesti.havelange.model.Team;
import com.mesti.havelange.repository.TeamRepository;
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

    public List<Team> getAll() {
        return teamRepository.findAll();
    }

    public Team getByID(long id) {
        return teamRepository.findById(id).orElseThrow();
    }

    public Team getByName(String name) {
        return teamRepository.findByName(name).orElseThrow();
    }

    public void delete(Long id) {
        var team = teamRepository.findById(id).orElseThrow();
        teamRepository.delete(team);
    }
}
