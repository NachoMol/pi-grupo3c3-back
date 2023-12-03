package com.explorer.equipo3.controller;

import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.model.Reservation;
import com.explorer.equipo3.model.dto.ReservationDTO;
import com.explorer.equipo3.service.IReservationService;
import com.explorer.equipo3.service.ReservationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/reservations")
public class ReservationController {

    @Autowired
    private IReservationService reservationService;

    @PostMapping("/create")
    public ResponseEntity<?> saveReservation(@Valid @RequestBody ReservationDTO reservationDTO) throws RuntimeException {
        try {
            Reservation newReservation = reservationService.saveReservation(reservationDTO);
            return ResponseEntity.status(HttpStatus.CREATED).body(newReservation);

        }catch (Exception e){
            e.printStackTrace();  // Imprime la traza de la excepción en la consola
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
    public ResponseEntity<List<Reservation>> getAllCurrentByProduct(@PathVariable Long id) {
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

    @GetMapping("/availableproducts")
    public ResponseEntity<Page<Product>> findAvailableProducts(
            @RequestParam(required = false) String productName,
            @RequestParam(required = false) List<Long> categoryIds,
            @RequestParam(required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkin,
            @RequestParam(required = false)@DateTimeFormat(pattern = "yyyy-MM-dd") LocalDate checkout,
            Pageable pageable) {
        Page<Product> availableProducts =
                reservationService.getProductsearch(productName, categoryIds, checkin, checkout, pageable);
        return new ResponseEntity<>(availableProducts, HttpStatus.OK);
    }


    @GetMapping("/availablereservations-user/{id}")
    public ResponseEntity<Page<Reservation>> findAvailableReservationsByUser(@PathVariable(name = "id", required = true) Long idUser,Pageable pageable) {
        Page<Reservation> availableProducts = reservationService.getAllReservationsByUser(idUser, pageable);
        return new ResponseEntity<>(availableProducts, HttpStatus.OK);
    }

    @PutMapping("/cancel/{id}")
    public ResponseEntity<?> cancelReservation (@PathVariable Long id){
        try {

            return  ResponseEntity.ok(reservationService.deleteReservationById(id));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

}
