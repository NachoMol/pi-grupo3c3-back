package com.equipo3.explorer.dto;

import com.equipo3.explorer.model.Role;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter @Setter
public class UserDTO {

    private Long id;
    private String firstName;
    private String lastName;
    private String username;
    private String password;
    private String email;
    private Integer phone;
    private LocalDate birthDate;
    private Role role;
}
