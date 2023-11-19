package com.explorer.equipo3.controller;

import com.explorer.equipo3.model.Reservation;
import com.explorer.equipo3.model.dto.ReservationDTO;
import com.explorer.equipo3.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private ReservationService reservationService;

    @PostMapping("/create")
    public ResponseEntity<?> saveReservation(@Valid @RequestBody ReservationDTO reservationDTO) throws RuntimeException {
        try {
            Reservation newReservation = reservationService.saveReservation(reservationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newReservation);

        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

    }

    @GetMapping("/{id}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long id) {
        return reservationService.getReservationById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }
    @GetMapping
    public ResponseEntity<Page<Reservation>> getAll(Pageable pageable) {
        Page<Reservation> reservation = reservationService.getAllReservations(pageable);
        return ResponseEntity.ok(reservation);
    }

    @GetMapping("/current/{id}")
    public ResponseEntity<List<Reservation>> getAllCurrentByProduct(Long id) {
        List<Reservation> reservation = reservationService.getReservatiosByProductscurrent(id);
        return ResponseEntity.ok(reservation);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long id, @Valid @RequestBody Reservation reservation) {
        try {
            Optional<Reservation> updateReservarion = reservationService.updateReservation(id, reservation);
            return ResponseEntity.ok(updateReservarion.orElseThrow());
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
