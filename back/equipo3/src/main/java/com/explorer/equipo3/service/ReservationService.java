package com.explorer.equipo3.service;

import com.explorer.equipo3.controller.ProductController;
import com.explorer.equipo3.exception.ReservationInvalidException;
import com.explorer.equipo3.model.City;
import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.model.Reservation;
import com.explorer.equipo3.model.User;
import com.explorer.equipo3.model.dto.ReservationDTO;
import com.explorer.equipo3.repository.IReservationRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PathVariable;

import javax.mail.MessagingException;
import javax.validation.Valid;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ReservationService implements IReservationService{

    private static final Logger logger = LogManager.getLogger(ProductService.class);

    @Autowired
    IReservationRepository iReservationRepository;

    @Autowired
    IUserService userService;
    @Autowired
    IProductService productService;
    @Autowired
    ICityService cityService;
    @Autowired
    private EmailService emailService;



    @Override
    public Page<Reservation> getAllReservations(Pageable pageable) {

        return iReservationRepository.findAll(pageable);
    }

    @Override
    public Page<Reservation> getAllReservationsByUser(Long idUser, Pageable pageable) {
        return iReservationRepository.findCurrentReservationsByUserId(idUser,pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Reservation> getReservationById(Long id) {

        return iReservationRepository.findById(id);
    }

    @Override
    @Transactional
    public Reservation saveReservation(@Valid  ReservationDTO reservationDTO) {
        Reservation reservation = new Reservation();
        Optional<Product> product = productService.getProductById(reservationDTO.getProduct_id());
        Optional<User> user = userService.getUserID(reservationDTO.getUser_id());
        Optional<City> city = cityService.getCityById(reservationDTO.getCity_id());
        reservation.setCity(city.orElseThrow());
        reservation.setProduct(product.orElseThrow());
        reservation.setUser(user.orElseThrow());
        reservation.setCheckin(reservationDTO.getCheckin());
        reservation.setCheckout(reservationDTO.getCheckout());
        logger.info("Precio del auto: " + reservation.getProduct().getPrice());
        logger.info("Cantidad de dias: " + ChronoUnit.DAYS.between(reservationDTO.getCheckin(), reservationDTO.getCheckout()));
        reservation.setPrice(ChronoUnit.DAYS.between(reservationDTO.getCheckin(), reservationDTO.getCheckout()) * reservation.getProduct().getPrice());
        logger.info("Precio de reserva: " + reservation.getPrice());
        reservation.setState(true);

        if (validateReservation(reservation)) {

            Reservation newReservation=iReservationRepository.save(reservation);
            sentMail(newReservation.getUser().getEmail(),
                    newReservation.getUser().getName()+" "+ newReservation.getUser().getLastname(),
                    newReservation.getProduct().getName(),
                    newReservation.getCheckin(),
                    newReservation.getCheckout());
            logger.info("reservation creada");
            return newReservation;

        } else {
            //Manejo de error o lanzar una excepción
            logger.info("entro a la exception");
            throw new ReservationInvalidException("La reserva no es válida");
        }

    }

    public ResponseEntity<?> sentMail(String email, String nameUser,String nameProduct, LocalDate checkin, LocalDate checkout){
        try {
            emailService.sendMailConfirmationReservation(email, nameUser, nameProduct, checkin, checkout);
            return new ResponseEntity<>("Correo de confirmación de Reservación enviado.", HttpStatus.OK);
        } catch (MessagingException e) {
            return new ResponseEntity<>("Error al enviar el correo de confirmación.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<?> reSentMail(String email, String nameUser,String nameProduct, LocalDate checkin, LocalDate checkout){
        try {
            emailService.reSendMailConfirmationReservation(email, nameUser, nameProduct, checkin, checkout);
            return new ResponseEntity<>("Correo de confirmación de Reservación enviado.", HttpStatus.OK);
        } catch (MessagingException e) {
            return new ResponseEntity<>("Error al enviar el correo de confirmación.", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


    @Override
    @Transactional
    public Optional<Reservation> updateReservation(Long id, Reservation reservation) {
        Optional<Reservation> reservationId = getReservationById(id);
        Reservation reservationResult = null;
        if(reservationId.isPresent() && validateReservation(reservationId.orElseThrow())){

                Reservation reservatiosUpdate = null;
                reservatiosUpdate = reservationId.orElseThrow();
                reservatiosUpdate.setCity(reservation.getCity());
                reservatiosUpdate.setPrice(reservation.getPrice());
                reservatiosUpdate.setUser(reservation.getUser());
                reservatiosUpdate.setCheckin(reservation.getCheckin());
                reservatiosUpdate.setCheckout(reservation.getCheckout());
                reservatiosUpdate.setProduct(reservation.getProduct());
                reservationResult = iReservationRepository.save(reservatiosUpdate);
                sentMail(reservation.getUser().getEmail(),
                        reservation.getUser().getName() + " " + reservation.getUser().getLastname(),
                        reservation.getProduct().getName(),
                        reservation.getCheckin(),
                        reservation.getCheckout());


        }
        return Optional.ofNullable(reservationResult);

    }

    @Override
    @Transactional
    public String deleteReservationById(Long id) {
        try {
         Optional<Reservation> reservation = getReservationById(id);
            LocalDate current = LocalDate.now();
            if (reservation.isPresent()) {
                if (reservation.get().getCheckin().isBefore(current) || reservation.get().getCheckin().isEqual(current)) {
                    return "No se puede cancelar la reserva";
                } else {
                    int updatedRows = iReservationRepository.cancelReservation(id);
                    if (updatedRows > 0) {
                        return "Reserva anulada";
                    } else {
                        return "No se pudo anular la reserva";
                    }
                }
            }
            return "Reserva no encontrada";
        } catch (Exception e) {
            // Manejar la excepción según tus necesidades
            return "Error al cancelar la reserva: " + e.getMessage();
        }
    }

    @Override
    @Transactional
    public List<Reservation> getReservatiosByProductscurrent(Long id) {
        return iReservationRepository.findCurrentReservationsByProductId(id);
    }

    @Override
    @Transactional
    public Page<Product> getProductsearch(String productName, List<Long> categoryIds, LocalDate checkin, LocalDate checkout, Pageable pageable) {
        return iReservationRepository.findAvailableProducts(productName,categoryIds,checkin,checkout,pageable);
    }

    //Validaciones
    private boolean validateReservation(Reservation reservation) {
        // Validar que no haya reservas existentes para el mismo producto en las fechas ingresadas
        List<Reservation> reservationExist = iReservationRepository.findByProductIdAndCheckinAndCheckout(
                reservation.getProduct().getId(), reservation.getCheckin(), reservation.getCheckout());
        return reservationExist.isEmpty();
    }




}
