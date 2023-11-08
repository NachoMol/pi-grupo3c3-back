package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Role;
import com.explorer.equipo3.model.User;
import com.explorer.equipo3.repository.IRoleRepository;
import com.explorer.equipo3.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class UserService implements IUserService {

    @Autowired
    private IUserRepository userRepository;

    @Autowired
    private IRoleRepository roleRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public Optional<User> getUserById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public Optional<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public User saveUser(User user) {
        String passwordEncripted = passwordEncoder.encode(user.getPassword());
        user.setPassword(passwordEncripted);
        Optional<Role> roleOptional = roleRepository.findByName("ROLE_USER");
        List<Role> roles = new ArrayList<>();
        if(roleOptional.isPresent()) {
            roles.add(roleOptional.orElseThrow());
        }
        user.setRoles(roles);
        return userRepository.save(user);
    }

    @Override
    public Optional<User> updateUser(Long id, User user) {
        return Optional.empty();
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }
}
