package com.mesti.havelange.configs;

import com.mesti.havelange.models.Player;
import com.mesti.havelange.models.Team;
import com.mesti.havelange.repositories.PlayerRepository;
import com.mesti.havelange.repositories.TeamRepository;
import com.mesti.havelange.models.users.User;
import com.mesti.havelange.repositories.UserRepository;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final PlayerRepository playerRepository;
    private final PasswordEncoder passwordEncoder;
    boolean alreadySetup = true;

    public SetupDataLoader(UserRepository userRepository, TeamRepository teamRepository, PlayerRepository playerRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.playerRepository = playerRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        User user = new User();
        user.setUsername("admin");
        user.setPassword(passwordEncoder.encode("test"));
        user.setEmail("amin@havelange.com");
        user.setEnabled(true);
        userRepository.saveAndFlush(user);

        Team team = new Team();
        team.setName("Ipolitis FC");
        team.setEmail("mail@mail.com");
        team.setPhone("+598 99947145");
        team.setCity("Montevideo");
        team.setShortname("IPO");
        team.setClubColors("Blue,White");
        teamRepository.saveAndFlush(team);

        Player player = new Player();
        player.setTeam(team);
        player.setName("Rogelio");
        player.setLastName("Roldan");
        player.setShirtNumber(12);
        player.setDateOfBirth(LocalDate.of(1992, 10, 5));
        player.setIdentityId("4.111.123-5");
        playerRepository.saveAndFlush(player);

        alreadySetup = true;
    }


}
