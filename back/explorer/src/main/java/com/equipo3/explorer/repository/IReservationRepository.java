package com.equipo3.explorer.repository;

import com.equipo3.explorer.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IReservationRepository extends JpaRepository<Reservation,Long> {
}
