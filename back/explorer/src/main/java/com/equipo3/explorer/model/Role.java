package com.equipo3.explorer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Entity
@Table(name = "role")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "role")
    @JsonIgnore
    private List<User> users;

    public Role() {
    }

    public Role(Long id, String name, List<User> users) {
        this.id = id;
        this.name = name;
        this.users = users;
    }
}
