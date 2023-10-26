package com.equipo3.explorer.repository;

import com.equipo3.explorer.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation,Long> {
}
