package com.mesti.havelange.rest;

import com.mesti.havelange.security.model.User;
import com.mesti.havelange.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@Slf4j
@RestController(MediaType.APPLICATION_JSON_VALUE)
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<User> userById(@PathVariable Long id) {
        var user = userService.getUserByID(id);
        if(user.isEmpty())
            throw new NoSuchElementException("No user with id ".concat(String.valueOf(id)));

        return new ResponseEntity<>(user.get(), HttpStatus.OK);
    }

    @GetMapping("/admin")
    public String helloAdmin() {
        return "Hello Admin";
    }

    @GetMapping
    public List<User> getAll() {
        log.info("Getting all Brands");
        return userService.getAll();
    }

}
