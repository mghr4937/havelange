package com.mesti.havelange.unit.controllers;

import com.mesti.havelange.utils.TestUtils;
import com.mesti.havelange.controllers.UserController;
import com.mesti.havelange.controllers.dto.security.UserDTO;
import com.mesti.havelange.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.mesti.havelange.utils.TestUtils.*;
import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;

import static org.mockito.BDDMockito.given;

@ExtendWith(MockitoExtension.class)
public class UserControllerTest {

    @Mock
    private UserService userService;

    @InjectMocks
    private UserController userController;

    @Test
    public void getAllUsers_shouldReturnListOfUsers() {
        // given
        given(userService.getAll()).willReturn(Arrays.asList(new UserDTO(), new UserDTO()));

        // when
        ResponseEntity<List<UserDTO>> result = userController.getAll();

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).hasSize(2);
    }

    @Test
    public void getUser_shouldReturnUser() {
        // given

        var user = getUserDTO(ID, "Test");
        given(userService.getUserByID(ID)).willReturn(user);

        // when
        ResponseEntity<UserDTO> result = userController.getById(ID);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(user);
    }

    @Test
    public void getUser_shouldReturnUserByUsername() {
        // given
        var user = TestUtils.getTestUserDTO();
        given(userService.getByUsername(TEST_USER)).willReturn(user);

        // when
        ResponseEntity<UserDTO> result = userController.getByUsername(TEST_USER);

        // then
        assertThat(result.getStatusCode()).isEqualTo(HttpStatus.OK);
        assertThat(result.getBody()).isEqualTo(user);
    }

}