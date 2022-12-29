package com.mesti.havelange.utils;

import com.github.javafaker.Faker;
import com.mesti.havelange.controllers.dto.security.UserDTO;
import com.mesti.havelange.models.Team;
import com.mesti.havelange.models.users.User;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestUtils {
    public static final String PWD = "password";
    public static final String PWD_HASH = "$2a$10$quZzGc/lGlSVr8gu2tHTiOaR6lEqnJE06FPV9PfhPObVsXfTwuQwi";
    public static final String TOKEN = "token";
    public static final String TEST_USER = "test_user";
    public static final String OTHER_USER = "test";
    public static final String ERROR_MSG = "Wrong username or password";

    private static final String EMAIL = "mail@mail.com";
    public static final long ID = 1L;

    public static final Faker FAKER = new Faker();

    public static List<Team> createRandomTeams(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createRandomTeam())
                .collect(Collectors.toList());
    }

    public static Team createRandomTeam() {
        Team team = new Team();
        team.setName(FAKER.team().name());
        team.setShortName(FAKER.team().creature());
        team.setCity(FAKER.address().city());
        team.setPhone(FAKER.phoneNumber().phoneNumber());
        team.setEmail(FAKER.internet().emailAddress());
        team.setClubColors(FAKER.color().name());
        return team;
    }

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
