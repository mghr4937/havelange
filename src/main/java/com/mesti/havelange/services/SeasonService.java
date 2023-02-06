package com.mesti.havelange.services;

import com.mesti.havelange.controllers.dto.SeasonDTO;
import com.mesti.havelange.models.Season;
import com.mesti.havelange.repositories.SeasonRepository;
import com.mesti.havelange.services.mapper.EntityDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;
import javax.persistence.EntityNotFoundException;

@Service
@Transactional
@Validated
@RequiredArgsConstructor
@Slf4j
public class SeasonService {

    private final SeasonRepository seasonRepository;


    public SeasonDTO getSeasonById(Long id) {
        var season = seasonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Season with id " + id + " not found"));

        return EntityDtoMapper.map(season, SeasonDTO.class);
    }

    public SeasonDTO updateSeason(Long id, SeasonDTO seasonDTO) {
        var season = seasonRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Season with id " + id + " not found"));

        season.setEndDate(seasonDTO.getEndDate());
        season.setStartDate(seasonDTO.getStartDate());

        season = seasonRepository.save(season);
        return EntityDtoMapper.map(season, SeasonDTO.class);
    }

    public SeasonDTO createSeason(SeasonDTO seasonDTO) {
        var season = EntityDtoMapper.map(seasonDTO, Season.class);
        season = seasonRepository.save(season);
        return EntityDtoMapper.map(season, SeasonDTO.class);
    }


}
