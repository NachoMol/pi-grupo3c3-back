package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.model.Reservation;
import com.explorer.equipo3.model.dto.ReservationDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface IReservationService {




    Page<Reservation> getAllReservations(Pageable pageable);
    Page<Reservation> getAllReservationsByUser(Long idUser,Pageable pageable);
    Optional<Reservation> getReservationById(Long id);
    Reservation saveReservation(ReservationDTO reservationDTO);
    Optional<Reservation> updateReservation(Long id, Reservation reservation);
    String deleteReservationById(Long id);

    List<Reservation>getReservatiosByProductscurrent(Long id);
    Page<Product> getProductsearch(String productName, List<Long> categoryIds, LocalDate checkin, LocalDate checkout, Pageable pageable);

}
