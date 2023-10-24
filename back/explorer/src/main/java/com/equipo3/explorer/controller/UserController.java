package com.equipo3.explorer.controller;

import com.equipo3.explorer.dto.UserDTO;
import com.equipo3.explorer.model.Role;
import com.equipo3.explorer.model.User;
import com.equipo3.explorer.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<String> getUserById(@PathVariable Long id){
        Optional<User> userSearch = userService.getUserById(id);
        if(userSearch.isPresent()){
            User userResponse = userSearch.get();
            return ResponseEntity.ok("The User is: " + userResponse);
        }else{
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("user NOT_FOUND");
        }
    }

    @PostMapping("/create")
    public ResponseEntity<User> addUser(@RequestBody UserDTO userDTO){
        return ResponseEntity.ok(userService.saveUser(userDTO));
    }

    @PutMapping("/update")
    public ResponseEntity<String> updateUser(@RequestBody UserDTO userDTO){
        ResponseEntity<String> response;
        if(userService.getUserById(userDTO.getId()).isPresent()){
            userService.updateUser(userDTO);
            response = ResponseEntity.ok("User updated");
        }else{
            response = ResponseEntity.status(HttpStatus.NOT_FOUND).body("User NOT_FOUND");
        }
        return response;
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        userService.deleteUserById(id);
        return ResponseEntity.ok("User deleted successfully");
    }
}
