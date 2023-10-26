package com.equipo3.explorer.service;

import com.equipo3.explorer.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface IReservationService {

    List<Reservation> getAllReservations();
    Optional<Reservation> getReservationById(Long id);
    Reservation saveReservation(Reservation res);
    Optional<Reservation> updateReservation(Long id, Reservation res);
    void deleteReservationById(Long id);
}
