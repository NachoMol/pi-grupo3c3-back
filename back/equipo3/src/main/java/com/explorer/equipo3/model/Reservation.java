package com.explorer.equipo3.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Temporal(TemporalType.DATE)
    @Column(name = "checkin", nullable = false)
    private Date checkin;
    @Temporal(TemporalType.DATE)
    @Column(name = "checkout", nullable = false)
    private Date checkout;
    @Column(name = "price", nullable = false)
    private Double price;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    public Reservation(Date checkin, Date checkout, Double price) {
        this.checkin = checkin;
        this.checkout = checkout;
        this.price = price;
    }
}
