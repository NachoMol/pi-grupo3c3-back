package com.explorer.equipo3.service;

import com.explorer.equipo3.repository.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class JpaUserDetailsService implements UserDetailsService {

    @Autowired
    private IUserRepository userRepository;

    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<com.explorer.equipo3.model.User> userOptional = userRepository.findByEmail(email);
        if(userOptional.isEmpty()){
            throw new UsernameNotFoundException(String.format("Username %s not exist!", email));
        }
        com.explorer.equipo3.model.User user = userOptional.orElseThrow();

        //obteniendo roles desde la base de datos
        List<GrantedAuthority> authorities = user.getRoles()
                .stream()
                .map(role -> new SimpleGrantedAuthority((role.getName())))
                .collect(Collectors.toList());
        
        return new User(user.getEmail(),
                user.getPassword(),
                true,
                true,
                true,
                true,
                authorities);
    }
}
