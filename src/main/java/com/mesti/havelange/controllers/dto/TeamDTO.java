package com.mesti.havelange.controllers.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.List;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Validated
public class TeamDTO {
    private Long id;
    @NotBlank
    @Size(max = 255)
    private String name;
    @NotBlank
    @Size(max = 3)
    private String shortName;
    @NotBlank
    @Size(max = 100)
    private String city;
    @NotBlank
    @Size(max = 30)
    private String phone;
    @NotBlank
    @Email
    @Size(max = 100)
    private String email;
    @Size(max = 50)
    private String clubColors;
    @JsonIgnoreProperties("team")
    private List<PlayerDTO> players;
    private boolean enabled = true;
}
