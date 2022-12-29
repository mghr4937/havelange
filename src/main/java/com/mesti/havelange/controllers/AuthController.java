package com.mesti.havelange.controllers;

import com.mesti.havelange.controllers.dto.security.AuthResponse;
import com.mesti.havelange.repositories.UserRepository;
import com.mesti.havelange.configs.security.JwtUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.persistence.EntityNotFoundException;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    private static final String ERROR_MSG = "Wrong username or password";
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthController(JwtUtils jwtUtils, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<AuthResponse> auth(@RequestParam("user") String username, @RequestParam("password") String pwd) throws EntityNotFoundException {
        var user = userRepository.findByUsername(username);

        if (user.isEmpty() || !passwordEncoder.matches(pwd, user.get().getPassword()))
            return new ResponseEntity<>(new AuthResponse(ERROR_MSG), HttpStatus.BAD_REQUEST);

        String token = jwtUtils.getJWTToken(username);
        user.get().setToken(token);
        userRepository.saveAndFlush(user.get());

        return new ResponseEntity<>(new AuthResponse(token, username, "Hola!"), HttpStatus.OK);
    }
}
