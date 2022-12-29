package com.mesti.havelange.rest;

import com.mesti.havelange.model.Team;
import com.mesti.havelange.service.TeamService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@Slf4j
@RestController()
@RequestMapping("/team")
@Validated
public class TeamController {
    private final TeamService teamService;

    public TeamController(TeamService teamService) {
        this.teamService = teamService;
    }

    @GetMapping(path="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<Team>> getAll() {
        return new ResponseEntity<>(teamService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Team> getById(@PathVariable Long id) {
        var team = teamService.getByID(id);
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    @GetMapping(path="/search", produces= MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Team> getByName(@Valid @RequestParam String name) {
        var team = teamService.getByName(name);
        return new ResponseEntity<>(team, HttpStatus.OK);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Team> delete(@PathVariable Long id){
        teamService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
