package com.equipo3.explorer.service;

import com.equipo3.explorer.dto.UserDTO;
import com.equipo3.explorer.model.User;
import com.equipo3.explorer.repository.IUserRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService{

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    ObjectMapper mapper;

    @Override
    public List<UserDTO> getAllUsers() {
        // A implementar con DTO o sin segun acordemos
        List<User> users = userRepository.findAll();
        List<UserDTO> usersDTO = new ArrayList<>();
        for(User user : users){
            usersDTO.add(mapper.convertValue(user, UserDTO.class));
        }

        return usersDTO;
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User saveUser(User user) {
        return userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {

        // A implementar segun requerimiento
        return null;
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
