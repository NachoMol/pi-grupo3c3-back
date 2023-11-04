package com.explorer.equipo3.controller;

import com.explorer.equipo3.exception.DuplicatedValueException;
import com.explorer.equipo3.model.User;
import com.explorer.equipo3.service.IUserService;
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
    private IUserService userService;

    @GetMapping()
    public ResponseEntity<List<User>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        Optional<User> userSearch = userService.getUserById(id);
        if(userSearch.isPresent()){
            return ResponseEntity.ok(userSearch.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email){
        Optional<User> userSearch = userService.getUserByEmail(email);
        if(userSearch.isPresent()){
            return ResponseEntity.ok(userSearch.orElseThrow());
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> addUser(@RequestBody User user) throws DuplicatedValueException {
        Optional<User> userOptional = userService.getUserByEmail(user.getEmail());
        if (userOptional.isEmpty()) {
            userService.saveUser(user);
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            throw new DuplicatedValueException("Email exist in Database");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user){
        Optional<User> userOptional = userService.updateUser(id, user);
        if(userOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        Optional<User> userOptional = userService.getUserById(id);
        if(userOptional.isPresent()){
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
