package com.explorer.equipo3.service;

import com.explorer.equipo3.model.User;
import com.explorer.equipo3.model.dto.UserDTO;

import java.util.List;
import java.util.Optional;

public interface IUserService {

    List<UserDTO> getAllUsers();
    Optional<User> getUserID(Long id);

    void updateAdminStatus(Long id, Boolean makeAdmin);

    Optional<UserDTO> getUserById(Long id);
    UserDTO saveUser(User user);
    Optional<UserDTO> updateUser(Long id, User user);
    void deleteUserById(Long id);

    Optional<User> getUserByEmail(String email);
}
