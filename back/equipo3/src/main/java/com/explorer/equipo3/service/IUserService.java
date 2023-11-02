package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<User> getAllUsers();
    Optional<User> getUserById(Long id);
    User saveUser(User user);
    Optional<User> updateUser(Long id, User user);
    void deleteUserById(Long id);
}
