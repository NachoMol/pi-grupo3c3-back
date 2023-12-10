package com.explorer.equipo3.Service;


import com.explorer.equipo3.model.Category;
import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.model.Reservation;
import com.explorer.equipo3.service.CategoryService;
import com.explorer.equipo3.service.ProductService;
import com.explorer.equipo3.service.ReservationService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.sql.Array;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@SpringBootTest
class ReservationServiceTest {

    @Autowired
    private static final Logger logger = LogManager.getLogger(ReservationService.class);
    @Autowired
    ReservationService reservationService;
    @Autowired
    ProductService productService;
    @Autowired
    CategoryService categoryService;

    @Test
    @Order(1)
    void getAllReservations() {
        logger.info("Comenzamos test de getAllReservations pageable");
        Pageable pageable = PageRequest.of(0, 10);
        Page<Reservation> getAllReservationsPageableTest = reservationService.getAllReservations(pageable);
        assertTrue(getAllReservationsPageableTest.getTotalElements() > 1);

    }

    @Test
    @Order(2)
    void getReservationById() {
        logger.info("Comenzamos test de getReservationById");
        Optional<Reservation> reservation = reservationService.getReservationById(12L);
        assertTrue(reservation.isPresent());
    }

    @Test
    @Order(3)
    void updateReservation() {
        logger.info("Comenzamos test de updateReservation");
        Optional<Reservation> reservation = reservationService.getReservationById(11L);
        Long id = reservation.get().getId();
        reservation.get().setState(true);
        reservation.get().setCheckin(LocalDate.now().plusDays(4L));
        reservation.get().setCheckout(LocalDate.now().plusDays(7L));
        Optional<Reservation> reservationUpdate = reservationService.updateReservation(id, reservation.orElseThrow());
        assertTrue(reservationUpdate.get().getId()== id);
    }

    @Test
    @Order(4)
    void deleteReservationById() {
        logger.info("Comenzamos test de deleteReservationById");
        Optional<Reservation> reservation = reservationService.getReservationById(11L);
        reservationService.deleteReservationById(reservation.get().getId());
        reservation = reservationService.getReservationById(11L);
        assertFalse(reservation.get().getState());
    }

    @Test
    @Order(5)
    void getReservatiosByProductscurrent() {
        logger.info("Comenzamos test de getReservatiosByProductscurrent");
        Optional<Reservation> reservation = reservationService.getReservationById(11L);
        Optional<Product> product = productService.getProductById(reservation.get().getProduct().getId());
        product.get().setState(true);
        productService.updateProduct(product.get().getId(), product.orElseThrow());
        List<Reservation> reservations = reservationService.getReservatiosByProductscurrent(reservation.get().getProduct().getId());
        assertTrue(reservations.size()>=1);
        // eliminamos el producto actualizado para no crear conflictos
        productService.deleteProductById(product.get().getId());

    }

    @Test
    @Order(6)
    void getProductsearch() {
        logger.info("Comenzamos test de getProductsearch pageable");
        Pageable pageable = PageRequest.of(0, 10);
        List<Category> categories = categoryService.getAllCategories();
        //guardamos los id de categorias
        List<Long> cateoriesIds =new ArrayList<>();
        for (int i=0 ; i<categories.size(); i++){
            cateoriesIds.add(categories.get(i).getId());
        }
        // String null para que no filtre por nombres
        String name = null;
        // le enviamos todas las categorias existentes y filtramos por fechas checkin corresponde a la actual y el checkout a 2 días más de la actual
        Page<Product> getProductsearchPageableTest = reservationService.getProductsearch(name,cateoriesIds,LocalDate.now(),LocalDate.now().plusDays(2L),pageable);
        assertTrue(getProductsearchPageableTest.getTotalElements() > 1);

    }
}