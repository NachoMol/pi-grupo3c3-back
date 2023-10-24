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
    public User saveUser(UserDTO userDTO) {
        User user = mapper.convertValue(userDTO, User.class);
        return userRepository.save(user);
    }

    @Override
    public User updateUser(UserDTO userDTO) {
        Optional<User> userExist = userRepository.findById(userDTO.getId());
        User user = mapper.convertValue(userDTO, User.class);
        if(userExist.isPresent()){
            user.setPassword(user.getEmail());
        }
        return userRepository.save(user);
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}