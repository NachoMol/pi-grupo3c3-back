package com.explorer.equipo3.repository;

import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation,Long> {



    //state es el estado de la reservación si está false está cancelada, si está en true está activa
    @Query("SELECT r FROM Reservation r JOIN r.product p WHERE p.id = :productoId " +
            "AND ((r.checkin BETWEEN :checkin AND :checkout) OR " +
            "(r.checkout BETWEEN :checkin AND :checkout))" +
            "And r.state = true")
    List<Reservation> findByProductIdAndCheckinAndCheckout(Long productoId,LocalDate checkin, LocalDate checkout);

    //state es el estado de la reservación si está false está cancelada, si está en true está activa
    @Query("SELECT r FROM Reservation r JOIN r.product p WHERE p.id = :productId AND r.checkout >= CURRENT_DATE AND r.state = true")
    List<Reservation> findCurrentReservationsByProductId(Long productId);

    @Query("SELECT r FROM Reservation r JOIN r.user u WHERE u.id = :userId")
    Page<Reservation> findCurrentReservationsByUserId(Long userId, Pageable pageable);


    //state es el estado de la reservación si está false está cancelada, si está en true está activa
    @Query("SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN p.reservations r " +
            "LEFT JOIN p.category c " +
            "WHERE (:productName IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :productName, '%'))) " +
            "AND (COALESCE(:categoryIds) IS NULL OR c.id IN (:categoryIds)) " +
            "AND  (r.state = true OR r.id IS NULL)"+
            "AND (r.id IS NULL OR (:checkin IS NULL AND :checkout IS NULL) OR NOT EXISTS (" +
            "   SELECT 1 FROM Reservation r2 " +
            "   WHERE r2.product = p " +
            "   AND (:checkin IS NOT NULL AND r2.checkout >= :checkin) " +
            "   AND (:checkout IS NOT NULL AND r2.checkin <= :checkout)))")
    Page<Product> findAvailableProducts(
            @Param("productName") String productName,
            @Param("categoryIds") List<Long> categoryIds,
            @Param("checkin") LocalDate checkin,
            @Param("checkout") LocalDate checkout,
            Pageable pageable
    );

    @Transactional
    @Modifying
    @Query("UPDATE Reservation r SET r.state = false WHERE r.id = :idReservation")
    int cancelReservation(@Param("idReservation") Long idReservation);

}
