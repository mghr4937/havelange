package com.mesti.havelange.configs;

import com.mesti.havelange.models.Team;
import com.mesti.havelange.repositories.TeamRepository;
import com.mesti.havelange.repositories.UserRepository;
import com.mesti.havelange.models.users.User;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
public class SetupDataLoader implements ApplicationListener<ContextRefreshedEvent> {
    boolean alreadySetup = false;
    private final UserRepository userRepository;
    private final TeamRepository teamRepository;
    private final PasswordEncoder passwordEncoder;

    public SetupDataLoader(UserRepository userRepository, TeamRepository teamRepository, PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.teamRepository = teamRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    @Transactional
    public void onApplicationEvent(ContextRefreshedEvent event) {

        if (alreadySetup)
            return;

        User user = new User();
        user.setUsername("Test");
        user.setPassword(passwordEncoder.encode("test"));
        user.setEmail("test@test.com");
        user.setEnabled(true);
        userRepository.save(user);

        Team team = new Team();
        team.setName("Ipolitis FC");
        team.setEmail("mail@mail.com");
        team.setPhone("+598 99947145");
        team.setCity("Montevideo");
        team.setShortName("IPO");
        team.setClubColors("Blue,White");
        teamRepository.saveAndFlush(team);

        alreadySetup = true;
    }


}
