package com.mesti.havelange;

import com.mesti.havelange.controllers.dto.UserDTO;
import com.mesti.havelange.models.users.User;

import java.util.Optional;


public class TestUtils {
    public static final String PWD = "password";
    public static final String PWD_HASH = "$2a$10$quZzGc/lGlSVr8gu2tHTiOaR6lEqnJE06FPV9PfhPObVsXfTwuQwi";
    public static final String TOKEN = "token";
    public static final String TEST_USER = "test_user";
    public static final String OTHER_USER = "test";
    public static final String ERROR_MSG = "Wrong username or password";

    public static Optional<User> getUser() {
        var user = new User();
        user.setId(1L);
        user.setUsername(TEST_USER);
        user.setPassword(PWD_HASH);

        return Optional.of(user);
    }

    public static UserDTO getUserDTO(long id, String username) {
        var user = new UserDTO();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(PWD_HASH);

        return user;
    }

    public static UserDTO getTestUserDTO() {
        var user = new UserDTO();
        user.setId(1L);
        user.setUsername(TEST_USER);
        user.setPassword(PWD_HASH);

        return user;
    }
}
