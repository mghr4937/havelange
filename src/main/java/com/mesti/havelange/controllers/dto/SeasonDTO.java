package com.mesti.havelange.controllers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import java.time.LocalDate;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Validated
public class SeasonDTO {
    private Long id;
    private Long tournamentId;
    private LocalDate startDate;
    private LocalDate endDate;
    private List<MatchDTO> matches;
}
