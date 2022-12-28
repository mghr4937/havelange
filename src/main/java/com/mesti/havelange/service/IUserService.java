package com.mesti.havelange.service;

import com.mesti.havelange.security.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> getAll();
    Optional<User> getUserByID(long id);
    Optional<User> getUserByUsername(String usernaame);


}
