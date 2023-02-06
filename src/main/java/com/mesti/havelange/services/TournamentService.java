package com.mesti.havelange.services;

import com.mesti.havelange.controllers.dto.tournament.TournamentDTO;
import com.mesti.havelange.models.Location;
import com.mesti.havelange.models.Team;
import com.mesti.havelange.models.Tournament;
import com.mesti.havelange.repositories.LocationRepository;
import com.mesti.havelange.repositories.MatchRepository;
import com.mesti.havelange.repositories.TeamRepository;
import com.mesti.havelange.repositories.TournamentRepository;
import com.mesti.havelange.services.mapper.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import javax.persistence.EntityNotFoundException;
import java.util.List;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
@Slf4j
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final LocationRepository locationRepository;
    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;

    public List<TournamentDTO> getAll() {
        var tournaments = tournamentRepository.findAll();
        return EntityDtoMapper.mapAll(tournaments, TournamentDTO.class);
    }

    public TournamentDTO getById(long id) {
        var tournament = tournamentRepository.getReferenceById(id);
        return EntityDtoMapper.map(tournament, TournamentDTO.class);
    }

    public TournamentDTO save(TournamentDTO tournamentDTO) {
        var tournament = EntityDtoMapper.map(tournamentDTO, Tournament.class);
        tournament = tournamentRepository.save(tournament);

        //Saving locations if present
        if (tournamentDTO.getLocations() != null) {
            var locations = EntityDtoMapper.mapAll(tournamentDTO.getLocations(), Location.class);
            for (Location location : locations) {
                location.setTournament(tournament);
            }
            locationRepository.saveAll(locations);
        }

        return EntityDtoMapper.map(tournament, TournamentDTO.class);
    }

    public void addTeamsToTournament(Long tournamentId, List<Long> teamIds) {
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new EntityNotFoundException("Tournament not found with id: " + tournamentId));
        List<Team> teams = teamRepository.findAllById(teamIds);
        tournament.getTeams().addAll(teams);
        tournamentRepository.save(tournament);
    }

    public void removeTeamFromTournament(Long tournamentId, Long teamId) {
        var tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new EntityNotFoundException("Tournament with id " + tournamentId + " not found"));
        var team = teamRepository.findById(teamId)
                .orElseThrow(() -> new EntityNotFoundException("Team with id " + teamId + " not found"));
        tournament.removeTeam(team);
        tournamentRepository.save(tournament);
    }

    public void disableTournament(Long id) {
        var tournament = tournamentRepository.getReferenceById(id);
        tournament.setEnabled(false);
        tournamentRepository.saveAndFlush(tournament);
    }



}
