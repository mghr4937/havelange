package com.mesti.havelange.controllers;

import com.mesti.havelange.configs.security.model.User;
import com.mesti.havelange.services.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping(path="/", produces = MediaType.APPLICATION_JSON_VALUE)
    public List<User> getAll() {
        return userService.getAll();
    }

    @GetMapping(path="/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getById(@PathVariable Long id) {
        var user = userService.getUserByID(id);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }

    @GetMapping(path="/search", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<User> getByUsername(@RequestParam String username) {
        var user = userService.getByUsername(username);
        return new ResponseEntity<>(user, HttpStatus.OK);
    }



}
