package com.mesti.havelange.rest;

import com.mesti.havelange.repository.UserRepository;
import com.mesti.havelange.security.JwtUtils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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

    @PostMapping
    public ResponseEntity<?> auth(@RequestParam("user") String username, @RequestParam("password") String pwd) {
        //TO-DO: check password on user auth
        try {
            var user = userRepository.findByUsername(username).orElseThrow(Exception::new);

            if (passwordEncoder.matches( pwd, user.getPassword())) {
                String token = jwtUtils.getJWTToken(username);
                user.setToken(token);
                userRepository.saveAndFlush(user);
            } else {
                throw new Exception();
            }
            return new ResponseEntity<>(user, HttpStatus.OK);

        } catch (Exception ex) {
            return ResponseEntity.badRequest().body("Error: El nombre de usuario o la contraseña son incorrectos");
        }
    }
}
