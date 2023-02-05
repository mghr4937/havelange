package com.mesti.havelange.controllers;

import com.mesti.havelange.controllers.dto.tournament.AddTeamsToTournamentDTO;
import com.mesti.havelange.controllers.dto.tournament.TournamentDTO;
import com.mesti.havelange.services.TournamentService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/tournament")
@RequiredArgsConstructor
@Validated
public class TournamentController {

    private final TournamentService tournamentService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TournamentDTO>> getAll() {
        return new ResponseEntity<>(tournamentService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TournamentDTO> getById(@PathVariable Long id) {
        var tournamentDTO = tournamentService.getById(id);
        return new ResponseEntity<>(tournamentDTO, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TournamentDTO> save(@Valid @RequestBody TournamentDTO tournamentDTO) {
        var createdTournamentDTO = tournamentService.save(tournamentDTO);
        return new ResponseEntity<>(createdTournamentDTO, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TournamentDTO> delete(@PathVariable Long id) {
        tournamentService.disableTournament(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/{id}/teams", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AddTeamsToTournamentDTO> addTeamsToTournament(@PathVariable Long tournamentId, @RequestBody List<Long> teamIds) {
        tournamentService.addTeamsToTournament(tournamentId, teamIds);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{tournamentId}/teams/{teamId}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> removeTeamFromTournament(@PathVariable Long tournamentId, @PathVariable Long teamId) {
        tournamentService.removeTeamFromTournament(tournamentId, teamId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
