package com.equipo3.explorer.service;

import com.equipo3.explorer.dto.UserDTO;
import com.equipo3.explorer.model.User;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<UserDTO> getAllUsers();
    Optional<User> getUserById(Long id);
    User saveUser(User user);
    User updateUser(User user);
    void deleteUserById(Long id);

}
