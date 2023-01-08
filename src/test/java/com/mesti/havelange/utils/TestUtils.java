package com.mesti.havelange.utils;

import com.github.javafaker.Faker;
import com.mesti.havelange.models.Location;
import com.mesti.havelange.models.Player;
import com.mesti.havelange.models.Team;
import com.mesti.havelange.models.Tournament;
import com.mesti.havelange.models.users.User;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class TestUtils {
    public static final String PWD = "password";
    public static final String TEST_USER = "test_user";
    public static final String OTHER_USER = "other_user";
    public static final long ID = 1L;
    public static final Faker FAKER = new Faker();
    private static final String EMAIL = "mail@mail.com";
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

    public static Tournament createRandomTournament() {
        Tournament tournament = new Tournament();
        tournament.setName(FAKER.esports().league().concat(" Cup"));
        tournament.setCountry(FAKER.country().name());
        tournament.setCity(FAKER.nation().capitalCity());
        tournament.setLocations(List.of());
        tournament.setTeams(new ArrayList<>());
        tournament.setEnabled(true);

        return tournament;
    }

    public static List<Location> createRandomLocations(Tournament tournament, int numLocations) {
        var locations = new ArrayList<Location>();
        for (int i = 0; i < numLocations; i++) {
            var location = new Location();
            location.setName(FAKER.funnyName().name().concat(" Arena"));
            location.setAddress(FAKER.address().fullAddress());
            location.setTournament(tournament);
            locations.add(location);
        }
        tournament.setLocations(locations);

        return tournament.getLocations();
    }

    public static Team createRandomTeamWithPlayers(int numPlayers) {
        var team = createRandomTeam();
        var players = new ArrayList<Player>();
        for (int i = 0; i < numPlayers; i++) {
            Player player = createRandomPlayer(team);
            players.add(player);
        }
        team.setPlayers(players);
        return team;
    }

    public static Player createRandomPlayer() {
        var team = createRandomTeam();
        return createRandomPlayer(team);
    }

    public static List<Player> createRandomPlayers(Team team, int numPlayers) {
        Player player = new Player();

        var players = new ArrayList<Player>();
        for (int i = 0; i < numPlayers; i++) {
            player = createRandomPlayer(team);
            player.setShirtNumber(i + 1);
            players.add(player);
        }
        team.setPlayers(players);

        return team.getPlayers();
    }

    public static Player createRandomPlayer(Team team) {

        Player player = new Player();
        player.setName(FAKER.name().firstName());
        player.setLastName(FAKER.name().lastName());
        player.setIdentityId(FAKER.idNumber().valid());
        player.setDateOfBirth(LocalDate.of(FAKER.number().numberBetween(1975, 2015),
                FAKER.number().numberBetween(1, 12),
                FAKER.number().numberBetween(1, 25)));
        player.setShirtNumber(FAKER.number().numberBetween(1, 99));
        player.setGender("M");
        player.setTeam(team);
        player.setEnabled(true);
        return player;
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

}
