package com.equipo3.explorer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Entity
@Table(name = "carType")
public class CarType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    @OneToMany(mappedBy = "carType")
    @JsonIgnore
    private List<Model> models;

    public CarType() {
    }

    public CarType(Long id, String name, List<Model> models) {
        this.id = id;
        this.name = name;
        this.models = models;
    }
}
