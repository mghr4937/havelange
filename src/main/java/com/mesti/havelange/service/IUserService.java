package com.mesti.havelange.service;

import com.mesti.havelange.security.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    Optional<User> getUserByID(long id);
    List<User> getAll();
}
