package com.mesti.havelange.rest;

import com.mesti.havelange.security.model.User;
import com.mesti.havelange.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController
@RequestMapping("/user")
@Validated
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public List<User> getAll() {
        log.info("Getting all Users");
        return userService.getAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> getById(@PathVariable Long id) {
        var user = userService.getUserByID(id);
        if(user.isEmpty())
            throw new NoSuchElementException("No user with id ".concat(String.valueOf(id)));

        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @GetMapping("/search")
    public ResponseEntity<User> getByUsername(@RequestParam String username) {
        var user = userService.getUserByUsername(username);
        if(user.isEmpty())
            throw new NoSuchElementException("No user with username ".concat(String.valueOf(username)));

        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }



}
