package com.mesti.havelange.unit;

import com.mesti.havelange.configs.security.JwtUtils;
import com.mesti.havelange.controllers.AuthController;
import com.mesti.havelange.controllers.dto.AuthResponse;
import com.mesti.havelange.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Objects;
import java.util.Optional;

import static com.mesti.havelange.TestUtils.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class AuthControllerTest {

    @Mock
    private UserRepository userRepository;
    @Mock
    private JwtUtils jwtUtils;
    @Mock
    private PasswordEncoder passwordEncoder;
    @InjectMocks
    private AuthController authController;


    @Test
    void authSuccess() {
        // Arrange
        var user = getUser();

        when(userRepository.findByUsername(any())).thenReturn(user);
        when(passwordEncoder.matches(PWD, PWD_HASH)).thenReturn(true);
        when(jwtUtils.getJWTToken(any())).thenReturn(TOKEN);

        // Act
        ResponseEntity<AuthResponse> response = authController.auth(TEST_USER, PWD);

        // Assert
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(TOKEN, Objects.requireNonNull(response.getBody()).getToken());
        assertEquals(TEST_USER, response.getBody().getUsername());
    }

    @Test
    void authUserFailure() {
        // Arrange
        when(userRepository.findByUsername(any())).thenReturn(Optional.empty());

        // Act
        ResponseEntity<AuthResponse> response = authController.auth(OTHER_USER, PWD);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ERROR_MSG, Objects.requireNonNull(response.getBody()).getMessage());
    }

    @Test
    void authPasswordFailure() {
        // Arrange
        var user = getUser();

        when(userRepository.findByUsername(any())).thenReturn(user);
        when(passwordEncoder.matches(OTHER_USER, PWD_HASH)).thenReturn(false);

        // Act
        ResponseEntity<AuthResponse> response = authController.auth(TEST_USER, OTHER_USER);

        // Assert
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals(ERROR_MSG, Objects.requireNonNull(response.getBody()).getMessage());
    }


}
