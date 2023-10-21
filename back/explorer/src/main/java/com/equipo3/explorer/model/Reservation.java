package com.equipo3.explorer.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;

@Getter
@Setter
@Entity
@Table(name = "reservation")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalDate checkin;
    private LocalDate checkout;
    private Double amount;
    @ManyToOne
    @JoinColumn(name = "id_user", nullable = false)
    private User user;
    @ManyToOne
    @JoinColumn(name = "id_car")
    private Car car;

    public Reservation() {
    }

    public Reservation(Long id, LocalDate checkin, LocalDate checkout, Double amount, User user, Car car) {
        this.id = id;
        this.checkin = checkin;
        this.checkout = checkout;
        this.amount = amount;
        this.user = user;
        this.car = car;
    }
}
