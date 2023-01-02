package com.mesti.havelange.controllers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Validated
public class PlayerTeamDTO {
    private Long id;
    private String name;
    private boolean enabled = true;
}
