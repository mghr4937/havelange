package com.mesti.havelange.controllers;

import com.mesti.havelange.controllers.dto.TeamDTO;
import com.mesti.havelange.services.TeamService;
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
@RequestMapping("/team")
@RequiredArgsConstructor
@Validated
public class TeamController {
    private final TeamService teamService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TeamDTO>> getAll() {
        return new ResponseEntity<>(teamService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamDTO> getById(@PathVariable Long id) {
        var teamDTO = teamService.getByID(id);
        return new ResponseEntity<>(teamDTO, HttpStatus.OK);
    }

    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamDTO> getByName(@Valid @RequestParam String name) {
        var teamDTO = teamService.getByName(name);
        return new ResponseEntity<>(teamDTO, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamDTO> save(@Valid @RequestBody TeamDTO teamDto) {
        var createdTeamDTO = teamService.save(teamDto);
        return new ResponseEntity<>(createdTeamDTO, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamDTO> delete(@PathVariable Long id) {
        teamService.disableTeam(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamDTO> update(@PathVariable Long id, @Valid @RequestBody TeamDTO teamDto) {
        var updatedTeamDTO = teamService.update(id, teamDto);
        return new ResponseEntity<>(updatedTeamDTO, HttpStatus.OK);
    }
}
