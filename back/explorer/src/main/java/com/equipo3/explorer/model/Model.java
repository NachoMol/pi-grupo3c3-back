package com.equipo3.explorer.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
@Entity
@Table(name = "model")
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String seats;
    @ManyToOne
    @JoinColumn(name = "brand_id", nullable = false)
    private Brand brand;
    @ManyToOne
    @JoinColumn(name = "cartype_id", nullable = false)
    private CarType carType;
    /*@ManyToMany(mappedBy = "models")*/
    /*@OneToMany(mappedBy = "model")*/
    @OneToMany(mappedBy = "model")
    @JsonIgnore
    private List<Car> cars;
    /*@ManyToMany
    @JoinTable(name = "model_has_color", joinColumns = @JoinColumn(name = "model_id_model", nullable = false),
            inverseJoinColumns = @JoinColumn(name = "color_id_color", nullable = false))
    private List<Color> colors;*/

    public Model() {
    }

    public Model(Long id, String name, String seats, Brand brand, CarType carType, List<Car> cars) {
        this.id = id;
        this.name = name;
        this.seats = seats;
        this.brand = brand;
        this.carType = carType;
        this.cars = cars;
    }
}
