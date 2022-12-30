package com.mesti.havelange.controllers;

import com.mesti.havelange.controllers.dto.TeamDTO;
import com.mesti.havelange.services.TeamService;
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
@Validated
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<TeamDTO>> getAll() {
        return new ResponseEntity<>(teamService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamDTO> getById(@PathVariable Long id) {
        var team = teamService.getByID(id);
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<TeamDTO> getByName(@Valid @RequestParam String name) {
        var team = teamService.getByName(name);
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<TeamDTO> save(@Valid @RequestBody TeamDTO teamDto) {
        var createdTeam = teamService.save(teamDto);
        return new ResponseEntity<>(createdTeam, HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<TeamDTO> delete(@PathVariable Long id) {
        teamService.disableTeam(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
