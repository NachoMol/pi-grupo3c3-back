package com.equipo3.explorer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Entity
@Table(name = "brand")
public class Brand {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private  String name;
    @OneToMany(mappedBy = "brand")
    @JsonIgnore
    private List<Model> models;

    public Brand() {
    }

    public Brand(Long id, String name, List<Model> models) {
        this.id = id;
        this.name = name;
        this.models = models;
    }
}
