package com.mesti.havelange.controllers.dto.security;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.validation.annotation.Validated;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@JsonInclude(JsonInclude.Include.NON_NULL)
@Validated
public class UserDTO {
    private Long id;
    private String username;
    @Email
    @NotEmpty
    private String email;
    private String token;
    private boolean enabled = true;
    private LocalDateTime lastRequestDate;
}
