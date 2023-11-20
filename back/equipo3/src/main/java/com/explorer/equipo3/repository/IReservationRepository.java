package com.explorer.equipo3.repository;

import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.model.Reservation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface IReservationRepository extends JpaRepository<Reservation,Long> {




    @Query("SELECT r FROM Reservation r JOIN r.product p WHERE p.id = :productoId " +
            "AND ((r.checkin BETWEEN :checkin AND :checkout) OR " +
            "(r.checkout BETWEEN :checkin AND :checkout))")
    List<Reservation> findByProductIdAndCheckinAndCheckout(Long productoId,Date checkin, Date checkout);

    @Query("SELECT r FROM Reservation r JOIN r.product p WHERE p.id = :productId AND r.checkout >= CURRENT_DATE")
    List<Reservation> findCurrentReservationsByProductId(Long productId);


    @Query("SELECT DISTINCT p FROM Product p " +
            "LEFT JOIN p.reservations r " +
            "LEFT JOIN p.category c " +
            "WHERE (:productName IS NULL OR LOWER(p.name) LIKE LOWER(CONCAT('%', :productName, '%'))) " +
            "AND (:categoryIds IS NULL OR c.id IN :categoryIds) " +
            "AND (r.id IS NULL OR (:checkin IS NULL AND :checkout IS NULL) OR NOT EXISTS (" +
            "   SELECT 1 FROM Reservation r2 " +
            "   WHERE r2.product = p " +
            "   AND (:checkin IS NOT NULL AND r2.checkout >= :checkin) " +
            "   AND (:checkout IS NOT NULL AND r2.checkin <= :checkout)))")
    Page<Product> findAvailableProducts(
            String productName,
            List<Long> categoryIds,
            Date checkin,
            Date checkout,
            Pageable pageable
    );



}
