package com.mesti.havelange.controllers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
@Validated
public class PlayerDTO {
    private Long id;
    @NotBlank
    @Size(max = 128)
    private String name;
    @NotBlank
    @Size(max = 128)
    private String lastName;
    @NotBlank
    @Size(max = 30)
    private String phone;
    @NotNull
    private LocalDate dateOfBirth;
    @Min(1)
    private int shirtNumber;
    private int age;
    private PlayerTeamDTO team;
    private boolean enabled = true;
    private String gender;
    @Size(max = 128)
    private String identityId;
}