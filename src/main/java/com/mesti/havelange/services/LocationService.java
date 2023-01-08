package com.mesti.havelange.services;

import com.mesti.havelange.controllers.dto.LocationDTO;
import com.mesti.havelange.models.Location;
import com.mesti.havelange.repositories.LocationRepository;
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
public class LocationService {

    private final LocationRepository locationRepository;
    private final TournamentRepository tournamentRepository;

    public List<LocationDTO> getAll() {
        var locations = locationRepository.findAll();
        return EntityDtoMapper.mapAll(locations, LocationDTO.class);
    }

    public LocationDTO getById(long id) {
        var location = locationRepository.getReferenceById(id);
        return EntityDtoMapper.map(location, LocationDTO.class);
    }

    public List<LocationDTO> getByTournamentId(long id) {
        var locations = locationRepository.findByTournamentId(id);
        return EntityDtoMapper.mapAll(locations, LocationDTO.class);
    }

    public LocationDTO save(LocationDTO locationDTO) {
        var tournament = tournamentRepository.getReferenceById(locationDTO.getTournamentId());
        var location = EntityDtoMapper.map(locationDTO, Location.class);

        location.setTournament(tournament);
        location = locationRepository.save(location);
        return EntityDtoMapper.map(location, LocationDTO.class);
    }

    public LocationDTO update(long id, LocationDTO locationDTO) {
        var existingLocation = locationRepository.getReferenceById(id);
        var tournament = tournamentRepository.getReferenceById(locationDTO.getTournamentId());

        existingLocation.setName(locationDTO.getName());
        existingLocation.setAddress(locationDTO.getAddress());
        existingLocation.setTournament(tournament);

        existingLocation = locationRepository.save(existingLocation);
        return EntityDtoMapper.map(existingLocation, LocationDTO.class);
    }

    public void delete(long id) {
        locationRepository.deleteById(id);
    }
}