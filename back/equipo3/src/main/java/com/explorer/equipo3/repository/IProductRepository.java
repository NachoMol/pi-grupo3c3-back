package com.explorer.equipo3.repository;

import com.explorer.equipo3.model.Detail;
import com.explorer.equipo3.model.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT distinct p FROM Product p " +
            "LEFT JOIN FETCH p.category " +
            "LEFT JOIN FETCH p.city " +
            "LEFT JOIN FETCH p.images " +
            "LEFT JOIN FETCH p.details")
    List<Product> findAll();



    @Query("SELECT p FROM Product p WHERE p.category.id in :category_id")
    List<Product> findByCategory_idIn(List<Long> category_id);

    @Query("SELECT p FROM Product p WHERE p.category.id in :category_id")
    Page<Product>findByCategory_idInPageable(List<Long>category_id,Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name%")
    Optional<Product> findByName(String name);

    Page<Product> findAll(Pageable pageable);



}
