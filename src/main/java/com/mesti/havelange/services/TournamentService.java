package com.mesti.havelange.services;

import com.mesti.havelange.controllers.dto.TournamentDTO;
import com.mesti.havelange.models.Location;
import com.mesti.havelange.models.Tournament;
import com.mesti.havelange.repositories.LocationRepository;
import com.mesti.havelange.repositories.TeamRepository;
import com.mesti.havelange.repositories.TournamentRepository;
import com.mesti.havelange.services.mapper.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
public class TournamentService {

    private final TournamentRepository tournamentRepository;
    private final LocationRepository locationRepository;
    private final TeamRepository teamRepository;

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

    public void disableTournament(Long id) {
        var tournament = tournamentRepository.getReferenceById(id);
        tournament.setEnabled(false);
        tournamentRepository.saveAndFlush(tournament);
    }




}
