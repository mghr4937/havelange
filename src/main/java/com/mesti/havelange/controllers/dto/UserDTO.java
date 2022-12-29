package com.mesti.havelange.controllers.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

@Data
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDTO {
    private Long id;
    private String username;
    @Email
    @NotEmpty
    private String email;
    @NotEmpty
    private String password;
    private String token;
    private boolean enabled;
}
