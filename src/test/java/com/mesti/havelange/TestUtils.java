package com.mesti.havelange;

import com.mesti.havelange.controllers.dto.UserDTO;
import com.mesti.havelange.models.users.User;

public class TestUtils {
    public static final String PWD = "password";
    public static final String PWD_HASH = "$2a$10$quZzGc/lGlSVr8gu2tHTiOaR6lEqnJE06FPV9PfhPObVsXfTwuQwi";
    public static final String TOKEN = "token";
    public static final String TEST_USER = "test_user";
    public static final String OTHER_USER = "test";
    public static final String ERROR_MSG = "Wrong username or password";
    private static final String EMAIL = "mail@mail.com";
    public static final long ID = 1L;

    public static User getTestUser() {
        var user = new User();
        user.setId(ID);
        user.setUsername(TEST_USER);
        user.setPassword(PWD_HASH);
        user.setEmail(EMAIL);

        return user;
    }

    public static User getUser(long id, String username) {
        var user = new User();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(PWD_HASH);
        user.setEmail(EMAIL);

        return user;
    }

    public static UserDTO getUserDTO(long id, String username) {
        var user = new UserDTO();
        user.setId(id);
        user.setUsername(username);
        user.setPassword(PWD_HASH);
        user.setEmail(EMAIL);

        return user;
    }

    public static UserDTO getTestUserDTO() {
        var user = new UserDTO();
        user.setId(ID);
        user.setUsername(TEST_USER);
        user.setPassword(PWD_HASH);
        user.setEmail(EMAIL);

        return user;
    }
}
