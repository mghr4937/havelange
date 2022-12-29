package com.mesti.havelange.unit.services;

import com.mesti.havelange.controllers.dto.security.UserDTO;
import com.mesti.havelange.models.users.User;
import com.mesti.havelange.repositories.UserRepository;
import com.mesti.havelange.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.persistence.EntityNotFoundException;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static com.mesti.havelange.utils.TestUtils.*;
import static com.mesti.havelange.utils.TestUtils.getTestUserDTO;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.catchThrowable;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @InjectMocks
    private UserService userService;

    @Test
    public void testGetAll_shouldReturnListOfUserDTO() {
        // Given
        var otherId = 2L;
        var user1 = getTestUser();
        var user2 = getUser(otherId, OTHER_USER);

        var users = Arrays.asList(user1, user2);
        when(userRepository.findAll()).thenReturn(users);

        var userDto1 = getTestUserDTO();
        var userDto2 = getUserDTO(otherId, OTHER_USER);

        List<UserDTO> expectedUserDtos = Arrays.asList(userDto1, userDto2);

        // When
        List<UserDTO> userDtos = userService.getAll();

        // Then
        assertThat(userDtos).isEqualTo(expectedUserDtos);
    }

    @Test
    public void testGetUserByID_shouldReturnUserDTO() {
        // Given
        var user = getTestUser();
        when(userRepository.getReferenceById(ID)).thenReturn(user);

        UserDTO expectedUserDto = getTestUserDTO();

        // When
        UserDTO userDto = userService.getUserByID(ID);

        // Then
        assertThat(userDto).isEqualTo(expectedUserDto);
    }

    @Test
    public void testGetUserByID_shouldThrowEntityNotFoundException() {
        // Given
        when(userRepository.getReferenceById(ID)).thenThrow(EntityNotFoundException.class);

        // When
        Throwable thrown = catchThrowable(() -> userService.getUserByID(ID));

        // Then
        assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void testGetByUsername_shouldReturnUserDTO() {
        // Given
        var user = getTestUser();
        when(userRepository.findByUsername(TEST_USER)).thenReturn(Optional.of(user));

        var expectedUserDto = getTestUserDTO();

        // When
        UserDTO userDto = userService.getByUsername(TEST_USER);

        // Then
        assertThat(userDto).isEqualTo(expectedUserDto);
    }

    @Test
    public void testGetByUsername_shouldThrowEntityNotFoundException() {
        // Given
        String username = OTHER_USER;
        when(userRepository.findByUsername(username)).thenThrow(EntityNotFoundException.class);

        // When
        Throwable thrown = catchThrowable(() -> userService.getByUsername(username));

        // Then
        assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
    }

    @Test
    public void testDisableUser_shouldDisableUser() {
        // Given
        long userId = 1L;

        User user = getTestUser();
        when(userRepository.getReferenceById(userId)).thenReturn(user);

        // When
        userService.disableUser(userId);

        // Then
        verify(userRepository).saveAndFlush(user);
        assertThat(user.isEnabled()).isFalse();
    }

    @Test
    public void testDisableUser_shouldThrowEntityNotFoundException() {
        // Given
        long userId = 1L;
        when(userRepository.getReferenceById(userId)).thenThrow(EntityNotFoundException.class);

        // When
        Throwable thrown = catchThrowable(() -> userService.disableUser(userId));

        // Then
        assertThat(thrown).isInstanceOf(EntityNotFoundException.class);
    }


}
