package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Role;
import com.explorer.equipo3.model.User;
import com.explorer.equipo3.model.dto.UserDTO;
import com.explorer.equipo3.model.dto.mapper.DTOMapperUser;
import com.explorer.equipo3.repository.IRoleRepository;
import com.explorer.equipo3.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<UserDTO> getAllUsers() {
        List<User> users = userRepository.findAll();

        return users
                .stream()
                .map(u -> DTOMapperUser.builder().setUser(u).build())
                .collect(Collectors.toList());
    }

    @Override
    public Optional<UserDTO> getUserById(Long id) {
        return userRepository.findById(id).map(u -> DTOMapperUser
                .builder()
                .setUser(u)
                .build());
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public UserDTO saveUser(User user) {
        String passwordEncripted = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncripted);
        Optional<Role> roleOptional = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        if(roleOptional.isPresent()) {
            roles.add(roleOptional.orElseThrow());
        }
        user.setRoles(roles);
        return DTOMapperUser.builder().setUser(userRepository.save(user)).build();
    }

    @Override
    public Optional<UserDTO> updateUser(Long id, User user) {
        Optional<User> userID = userRepository.findById(id);
        User userOptional = null;
        if (userID.isPresent()) {
            User userDB = userID.orElseThrow();
            userDB.setEmail(user.getEmail());
            userOptional = userRepository.save(userDB);
        }

        return Optional.ofNullable(DTOMapperUser.builder().setUser(userOptional).build());
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
