package com.explorer.equipo3.controller;

import com.explorer.equipo3.exception.DuplicatedValueException;
import com.explorer.equipo3.model.User;
import com.explorer.equipo3.model.dto.UserDTO;
import com.explorer.equipo3.service.EmailService;
import com.explorer.equipo3.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private IUserService userService;

    @Autowired
    private EmailService emailService;

    @GetMapping()
    public ResponseEntity<List<UserDTO>> getAllUsers(){
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable Long id){
        Optional<UserDTO> userSearch = userService.getUserById(id);
        if(userSearch.isPresent()){
            return ResponseEntity.ok(userSearch.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/email/{email}")
    public ResponseEntity<?> getUserByEmail(@PathVariable String email){
        Optional<User> userSearch = userService.getUserByEmail(email);
        if(userSearch.isPresent()){
            User user = userSearch.orElseThrow();
            boolean isAdmin = user.getRoles().stream()
                    .anyMatch(role -> role.getName().equals("ROLE_ADMIN"));
            user.setAdmin(isAdmin);
            System.out.println("isAdmin: " + isAdmin);
            return ResponseEntity.ok(user);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/create")
    public ResponseEntity<?> addUser(@RequestBody User user) throws DuplicatedValueException {
        Optional<User> userOptional = userService.getUserByEmail(user.getEmail());
        if (userOptional.isEmpty()) {
            userService.saveUser(user);
            sentMail(user.getEmail(),user.getName()+" "+user.getLastname());
            return ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            throw new DuplicatedValueException("Email exist in Database");
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateUser(@PathVariable Long id, @RequestBody User user){
        Optional<UserDTO> userOptional = userService.updateUser(id, user);
        if(userOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.OK).build();
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/updateAdmin/{id}")
    public ResponseEntity<?> updateAdminStatus(@PathVariable Long id, @RequestParam Boolean makeAdmin) {
        try {
            userService.updateAdminStatus(id, makeAdmin);
            return ResponseEntity.status(HttpStatus.OK).build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error updating admin status");
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteUser(@PathVariable Long id){
        Optional<UserDTO> userOptional = userService.getUserById(id);
        if(userOptional.isPresent()){
            userService.deleteUserById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/confirmation/{email}")
    public ResponseEntity<?> sentMail(@PathVariable String email, String name){
        try {
            emailService.sendMailConfirmation(email, name);
            return new ResponseEntity<>("Usuario registrado con éxito. Se ha enviado un correo de confirmación.", HttpStatus.OK);
        } catch (MessagingException e) {
            return new ResponseEntity<>("Error al enviar el correo de confirmación.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/forwardmail/{email}")
    public ResponseEntity<?> forwardMail(@PathVariable String email){
        Optional<User> user = userService.getUserByEmail(email);
        if (user.isPresent()){
            sentMail(user.get().getEmail(),user.get().getName()+" "+user.get().getLastname());
            return new ResponseEntity<>("¡Correo reenviado con exito!.", HttpStatus.OK);
        }else {
            return  new ResponseEntity<>("Usuario no Registrado",HttpStatus.BAD_REQUEST);
        }

    }



}
