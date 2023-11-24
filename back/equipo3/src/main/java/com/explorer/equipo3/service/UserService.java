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
    public Optional<User> getUserID(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void updateAdminStatus(Long id, Boolean makeAdmin) {
        userRepository.findById(id).ifPresent(user -> {
            user.setAdmin(makeAdmin);
            userRepository.save(user);
        });
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

        // Obtener roles existentes del usuario
        List<Role> userRoles = user.getRoles();

        // Buscar el rol "ROLE_USER" y agregarlo si no existe
        Optional<Role> userRoleOptional = roleRepository.findByName("ROLE_USER");
        Role userRole = userRoleOptional.orElseThrow();
        if (!userRoles.contains(userRole)) {
            userRoles.add(userRole);
        }

        // Buscar el rol "ROLE_ADMIN" y agregarlo si el usuario es admin
        if (user.isAdmin()) {
            Optional<Role> adminRoleOptional = roleRepository.findByName("ROLE_ADMIN");
            Role adminRole = adminRoleOptional.orElseThrow();
            if (!userRoles.contains(adminRole)) {
                userRoles.add(adminRole);
            }
        }

        // Actualizar roles del usuario
        user.setRoles(userRoles);

        return DTOMapperUser.builder().setUser(userRepository.save(user)).build();
    }

    @Override
    public Optional<UserDTO> updateUser(Long id, User user) {

        System.out.println("Received request to update user with ID: " + id);
        System.out.println("User isAdmin: " + user.isAdmin());

        Optional<User> findUserById = userRepository.findById(id);
        User userUpdate = null;

        if (findUserById.isPresent()) {
            User userDB = findUserById.get();

            // Limpiar roles existentes
            userDB.getRoles().clear();

            // Agregar ROLE_USER si no es admin
            if (!user.isAdmin()) {
                Optional<Role> userRoleOptional = roleRepository.findByName("ROLE_USER");
                userRoleOptional.ifPresent(userDB::addRole);
            }

            // Agregar ROLE_ADMIN si es admin
            if (user.isAdmin()) {
                Optional<Role> adminRoleOptional = roleRepository.findByName("ROLE_ADMIN");
                adminRoleOptional.ifPresent(userDB::addRole);
            }

            // Actualizar otros detalles del usuario
            if(user.getEmail() != null){
            userDB.setEmail(user.getEmail());}

            userUpdate = userRepository.save(userDB);
        }
        System.out.println("Updated user with ID: " + userUpdate.getId());
        System.out.println("User isAdmin: " + user.isAdmin());
        return Optional.ofNullable(DTOMapperUser.builder().setUser(userUpdate).build());
    }

    @Override
    public void deleteUserById(Long id) {
        userRepository.deleteById(id);
    }

}
