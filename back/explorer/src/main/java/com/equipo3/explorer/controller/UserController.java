package com.equipo3.explorer.controller;

import com.equipo3.explorer.dto.UserDTO;
import com.equipo3.explorer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/allUsers")
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }
}
