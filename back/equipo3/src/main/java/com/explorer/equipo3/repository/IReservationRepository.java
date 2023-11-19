package com.explorer.equipo3.repository;

import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.model.Reservation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation,Long> {


    @Query("SELECT r FROM Reservation r JOIN r.product p WHERE p.id = :productoId " +
            "AND ((r.checkin BETWEEN :checkin AND :checkout) OR " +
            "(r.checkout BETWEEN :checkin AND :checkout))")
    List<Reservation> findByProductIdAndCheckinAndCheckout(Long productoId,Date checkin, Date checkout);

    @Query("SELECT r FROM Reservation r JOIN r.product p WHERE p.id = :productId ")
    List<Reservation> findCurrentReservationsByProductId(Long productId);
}
