package com.equipo3.explorer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Entity
@Table(name = "color")
public class Color {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @ManyToMany(mappedBy = "colors")
    private List<Model> models;

    public Color() {
    }

    public Color(Long id, String name, List<Model> models) {
        this.id = id;
        this.name = name;
        this.models = models;
    }
}
