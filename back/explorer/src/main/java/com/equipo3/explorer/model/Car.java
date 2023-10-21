package com.equipo3.explorer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Entity
@Table(name = "car")
public class Car {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Double price;
    private int stock;
    @ManyToMany
    @JoinTable(name = "model_has_car", joinColumns = @JoinColumn(name = "car_id_car", nullable = false),
               inverseJoinColumns = @JoinColumn(name = "model_id_model", nullable = false))
    private List<Model> models;

    public Car() {
    }

    public Car(Long id, Double price, int stock, List<Model> models) {
        this.id = id;
        this.price = price;
        this.stock = stock;
        this.models = models;
    }
}
