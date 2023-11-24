package com.explorer.equipo3.model;

import com.explorer.equipo3.validations.ValidateReservationDates;
import lombok.*;
import javax.persistence.*;
import javax.validation.constraints.Future;
import javax.validation.constraints.FutureOrPresent;
import java.time.LocalDate;
import java.util.Date;

@Getter @Setter @NoArgsConstructor @AllArgsConstructor @ToString
@Entity
@ValidateReservationDates
@Table(name = "reservations")
public class Reservation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @FutureOrPresent
    @Column(columnDefinition = "DATE", name = "checkin", nullable = false)
    private LocalDate checkin;

    @Column(columnDefinition = "DATE",name = "checkout", nullable = false)
    @Future
    private LocalDate checkout;
    @Column(name = "price", nullable = false)
    private Double price;
    @Column(name = "state")
    private Boolean state ;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;
    @ManyToOne
    @JoinColumn(name = "city_id")
    private City city;

    public Reservation(LocalDate checkin, LocalDate checkout, Double price) {
        this.checkin = checkin;
        this.checkout = checkout;
        this.price = price;
        this.state = true;
    }


}
