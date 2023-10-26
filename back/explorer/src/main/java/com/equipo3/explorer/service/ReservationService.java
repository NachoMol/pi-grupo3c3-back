package com.equipo3.explorer.service;

import com.equipo3.explorer.model.Reservation;
import com.equipo3.explorer.repository.IReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ReservationService implements IReservationService {

    @Autowired
    private IReservationRepository reservationRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Reservation> getAllReservations() {
        return reservationRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Reservation> getReservationById(Long id) {
        return reservationRepository.findById(id);
    }

    @Override
    @Transactional
    public Reservation saveReservation(Reservation res) {
        return reservationRepository.save(res);
    }

    @Override
    @Transactional
    public Optional<Reservation> updateReservation(Long id, Reservation res) {
        Optional<Reservation> reservationExist = reservationRepository.findById(id);
        Reservation reservationOptional = null;
        if(reservationExist.isPresent()){
            Reservation reservationDB = reservationExist.orElseThrow();
            reservationDB.setCheckin(res.getCheckin());
            reservationDB.setCheckout(res.getCheckout());
            reservationOptional = reservationRepository.save(reservationDB);
        }
        return Optional.ofNullable(reservationOptional);
    }

    @Override
    @Transactional
    public void deleteReservationById(Long id) {
        reservationRepository.deleteById(id);
    }
}
