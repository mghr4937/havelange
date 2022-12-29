package com.mesti.havelange.controllers;

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

import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/auth")
public class AuthController {
    private final JwtUtils jwtUtils;
    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;

    public AuthController(JwtUtils jwtUtils, PasswordEncoder passwordEncoder, UserRepository userRepository) {
        this.jwtUtils = jwtUtils;
        this.passwordEncoder = passwordEncoder;
        this.userRepository = userRepository;
    }

    @PostMapping(produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> auth(@RequestParam("user") String username, @RequestParam("password") String pwd) throws NoSuchElementException {
        var user = userRepository.findByUsername(username);

        if (user.isEmpty() || !passwordEncoder.matches(pwd, user.get().getPassword()))
            return new ResponseEntity<>("Error: El nombre de usuario o la contraseña son incorrectos", HttpStatus.BAD_REQUEST);

        String token = jwtUtils.getJWTToken(username);
        user.get().setToken(token);
        userRepository.saveAndFlush(user.get());

        return new ResponseEntity<>(user, HttpStatus.OK);

    }
}
