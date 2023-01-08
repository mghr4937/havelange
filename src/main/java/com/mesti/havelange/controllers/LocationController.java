package com.mesti.havelange.controllers;

import com.mesti.havelange.controllers.dto.LocationDTO;
import com.mesti.havelange.services.LocationService;
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
@RequestMapping("/location")
@RequiredArgsConstructor
@Validated
public class LocationController {
    private final LocationService locationService;

    @GetMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LocationDTO>> getAll() {
        return new ResponseEntity<>(locationService.getAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LocationDTO> getById(@PathVariable Long id) {
        var location = locationService.getById(id);
        return new ResponseEntity<>(location, HttpStatus.OK);
    }

    @GetMapping(path = "/tournament/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<LocationDTO>> getByTournamentId(@PathVariable Long id) {
        var locations = locationService.getByTournamentId(id);
        return new ResponseEntity<>(locations, HttpStatus.OK);
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LocationDTO> save(@Valid @RequestBody LocationDTO locationDTO) {
        var createdLocation = locationService.save(locationDTO);
        return new ResponseEntity<>(createdLocation, HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> delete(@PathVariable Long id) {
        locationService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<LocationDTO> update(@PathVariable Long id, @Valid @RequestBody LocationDTO locationDTO) {
        var updatedLocation = locationService.update(id, locationDTO);
        return new ResponseEntity<>(updatedLocation, HttpStatus.OK);
    }
}
