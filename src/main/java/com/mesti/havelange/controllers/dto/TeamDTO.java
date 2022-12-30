package com.mesti.havelange.controllers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class TeamDTO {
    private Long id;
    @NotBlank
    @Size(max = 3)
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
    private boolean enabled = true;
}
