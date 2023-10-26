package com.equipo3.explorer.controller;

import com.equipo3.explorer.model.Reservation;
import com.equipo3.explorer.service.IReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("reservations")
public class ReservationController {

    @Autowired
    private IReservationService reservationService;

    @GetMapping()
    public ResponseEntity<List<Reservation>> getAllReservations(){
        return ResponseEntity.ok(reservationService.getAllReservations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getReservationById(@PathVariable Long id){
        Optional<Reservation> rsvSearch = reservationService.getReservationById(id);
        if(rsvSearch.isPresent()){
            return ResponseEntity.ok(rsvSearch.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> addReservation(@RequestBody Reservation rsv){
        return ResponseEntity.status(HttpStatus.CREATED).body(reservationService.saveReservation(rsv));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> editReservation(@PathVariable Long id, @RequestBody Reservation rsv){
        Optional<Reservation> rsvOptional = reservationService.updateReservation(id, rsv);
        if(rsvOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteReservation(@PathVariable Long id){
        Optional<Reservation> rsvOptional = reservationService.getReservationById(id);
        if(rsvOptional.isPresent()){
            reservationService.deleteReservationById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
