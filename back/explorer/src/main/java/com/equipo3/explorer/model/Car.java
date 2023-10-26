package com.equipo3.explorer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NonNull;
import lombok.Setter;
import org.springframework.lang.Nullable;

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
    /*@ManyToMany
    @JoinTable(name = "model_has_car", joinColumns = @JoinColumn(name = "car_id_car", nullable = false),
               inverseJoinColumns = @JoinColumn(name = "model_id_model", nullable = false))*/

    @ManyToOne(optional = false)
    @JoinColumn(name = "model_id", nullable = false)
    private Model model;

    public Car() {
    }

    public Car(Long id, Double price, int stock, Model model) {
        this.id = id;
        this.price = price;
        this.stock = stock;
        this.model = model;
    }
}
