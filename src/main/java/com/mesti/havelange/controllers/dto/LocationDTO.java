package com.mesti.havelange.controllers.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class LocationDTO {
    private Long id;
    @NotBlank
    private String name;
    @NotBlank
    private String address;
    @NotNull
    private Long tournamentId;
}
