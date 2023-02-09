package com.mesti.havelange.services;

import com.mesti.havelange.controllers.dto.security.UserDTO;
import com.mesti.havelange.models.users.User;
import com.mesti.havelange.repositories.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class UserServiceTest {

    @Mock
    private UserRepository mockUserRepository;

    private UserService userServiceUnderTest;

    @BeforeEach
    void setUp() {
        userServiceUnderTest = new UserService(mockUserRepository);
    }

    @Test
    void testGetAll() {
        // Setup
        final List<UserDTO> expectedResult = List.of(
                new UserDTO(0L, "username", "email", "token", false, LocalDateTime.of(2020, 1, 1, 0, 0, 0)));

        // Configure UserRepository.findAll(...).
        final User user = new User();
        user.setId(0L);
        user.setUsername("username");
        user.setEmail("email");
        user.setPassword("password");
        user.setToken("token");
        user.setEnabled(false);
        user.setLastRequestDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final List<User> users = List.of(user);
        when(mockUserRepository.findAll()).thenReturn(users);

        // Run the test
        final List<UserDTO> result = userServiceUnderTest.getAll();

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetAll_UserRepositoryReturnsNoItems() {
        // Setup
        when(mockUserRepository.findAll()).thenReturn(Collections.emptyList());

        // Run the test
        final List<UserDTO> result = userServiceUnderTest.getAll();

        // Verify the results
        assertThat(result).isEqualTo(Collections.emptyList());
    }

    @Test
    void testGetUserByID() {
        // Setup
        final UserDTO expectedResult = new UserDTO(0L, "username", "email", "token", false, LocalDateTime.of(2020, 1, 1, 0, 0, 0));

        // Configure UserRepository.getReferenceById(...).
        final User user = new User();
        user.setId(0L);
        user.setUsername("username");
        user.setEmail("email");
        user.setPassword("password");
        user.setToken("token");
        user.setEnabled(false);
        user.setLastRequestDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockUserRepository.getReferenceById(0L)).thenReturn(user);

        // Run the test
        final UserDTO result = userServiceUnderTest.getUserByID(0L);

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetByUsername() {
        // Setup
        final UserDTO expectedResult = new UserDTO(0L, "username", "email", "token", false, LocalDateTime.of(2020, 1, 1, 0, 0, 0));

        // Configure UserRepository.findByUsername(...).
        final User user1 = new User();
        user1.setId(0L);
        user1.setUsername("username");
        user1.setEmail("email");
        user1.setPassword("password");
        user1.setToken("token");
        user1.setEnabled(false);
        user1.setLastRequestDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        final Optional<User> user = Optional.of(user1);
        when(mockUserRepository.findByUsername("username")).thenReturn(user);

        // Run the test
        final UserDTO result = userServiceUnderTest.getByUsername("username");

        // Verify the results
        assertThat(result).isEqualTo(expectedResult);
    }

    @Test
    void testGetByUsername_UserRepositoryReturnsAbsent() {
        // Setup
        when(mockUserRepository.findByUsername("username")).thenReturn(Optional.empty());

        // Run the test
        assertThatThrownBy(() -> userServiceUnderTest.getByUsername("username")).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    void testDisableUser() {
        // Setup
        // Configure UserRepository.getReferenceById(...).
        final User user = new User();
        user.setId(0L);
        user.setUsername("username");
        user.setEmail("email");
        user.setPassword("password");
        user.setToken("token");
        user.setEnabled(true);
        user.setLastRequestDate(LocalDateTime.of(2020, 1, 1, 0, 0, 0));
        when(mockUserRepository.getReferenceById(0L)).thenReturn(user);

        // Run the test
        userServiceUnderTest.disableUser(0L);

        // Verify the results
        verify(mockUserRepository).saveAndFlush(user);
        assertFalse(user.isEnabled());
    }
}
