package com.mesti.havelange.services;

import com.mesti.havelange.controllers.dto.UserDTO;
import com.mesti.havelange.repositories.UserRepository;
import com.mesti.havelange.models.users.User;
import com.mesti.havelange.services.mapper.EntityDtoMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public List<UserDTO> getAll() {
        List<User> users = userRepository.findAll();
        return  EntityDtoMapper.mapAll(users, UserDTO.class);
    }

    public UserDTO getUserByID(long id) {
        var user = userRepository.findById(id).orElseThrow();
        return EntityDtoMapper.map(user, UserDTO.class);
    }

    public UserDTO getByUsername(String username) {
        var user = userRepository.findByUsername(username).orElseThrow();
        return EntityDtoMapper.map(user, UserDTO.class);
    }

    public void disableUser(Long id){
        var user = userRepository.findById(id).orElseThrow();
        user.setEnabled(false);
        userRepository.saveAndFlush(user);
    }

}
