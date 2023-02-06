package com.mesti.havelange.controllers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Validated
public class MatchDTO {
    private Long id;
    @NotBlank
    private Long homeTeamId;
    @NotBlank
    private Long awayTeamId;
    @NotBlank
    private Long locationId;
    @NotBlank
    private Long tournamentId;
    @NotBlank
    private Long seasonId;
    @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private LocalDateTime startTime;
    private Integer homeTeamScore;
    private Integer awayTeamScore;
}
