package com.mesti.havelange.controllers;

import com.mesti.havelange.controllers.dto.PlayerDTO;
import com.mesti.havelange.services.PlayerService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/player")
@RequiredArgsConstructor
@Validated
public class PlayerController {

    private final PlayerService playerService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlayerDTO>> getAll() {
        return new ResponseEntity<>(playerService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerDTO> getById(@PathVariable Long id) {
        var player = playerService.getById(id);
        return new ResponseEntity<>(player, HttpStatus.OK);
    }

    @GetMapping(path = "/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<PlayerDTO>> getByTeamId(@Valid @RequestParam Long teamId) {
        var players = playerService.getByTeamId(teamId);
        return new ResponseEntity<>(players, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerDTO> save(@Valid @RequestBody PlayerDTO playerDTO) {
        var createdPlayer = playerService.save(playerDTO);
        return new ResponseEntity<>(createdPlayer, HttpStatus.CREATED);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<PlayerDTO> update(@PathVariable Long id, @Valid @RequestBody PlayerDTO playerDTO) {
        var updatedPlayer = playerService.update(id, playerDTO);

        return new ResponseEntity<>(updatedPlayer, HttpStatus.OK);
    }
}
