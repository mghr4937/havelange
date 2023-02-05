package com.mesti.havelange.controllers.dto.tournament;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.mesti.havelange.controllers.dto.LocationDTO;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.NotBlank;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TournamentDTO {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String country;
    @NotBlank
    private String city;
    private List<LocationDTO> locations;
    private List<TeamSummaryDTO> teams;
    private boolean enabled;

}

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Validated
class TeamSummaryDTO {

    private Long id;
    private String name;


}
