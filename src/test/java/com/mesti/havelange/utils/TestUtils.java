package com.mesti.havelange.utils;

import com.github.javafaker.Faker;
import com.mesti.havelange.controllers.dto.security.UserDTO;
import com.mesti.havelange.models.Team;
import com.mesti.havelange.models.users.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestUtils {
    public static final String PWD = "password";
    public static final String TEST_USER = "test_user";
    public static final String OTHER_USER = "other_user";
    private static final String EMAIL = "mail@mail.com";
    public static final long ID = 1L;

    public static final Faker FAKER = new Faker();
    private static final PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public static List<Team> createRandomTeams(int count) {
        return IntStream.range(0, count)
                .mapToObj(i -> createRandomTeam())
                .collect(Collectors.toList());
    }

    public static Team createRandomTeam() {
        Team team = new Team();
        team.setId(FAKER.number().randomNumber());
        team.setName(FAKER.team().name());
        team.setShortname(team.getName());
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
        user.setPassword(passwordEncoder.encode(PWD));
        user.setEmail(EMAIL);

        return user;
    }

    public static User getUser(String username) {
        var user = new User();

        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(PWD));
        user.setEmail(EMAIL);

        return user;
    }

    public static UserDTO getUserDTO(long id, String username) {
        var user = new UserDTO();
        user.setId(id);
        user.setUsername(username);
        user.setEmail(EMAIL);

        return user;
    }

    public static UserDTO getTestUserDTO() {
        var user = new UserDTO();
        user.setId(ID);
        user.setUsername(TEST_USER);
        user.setEmail(EMAIL);

        return user;
    }
}
