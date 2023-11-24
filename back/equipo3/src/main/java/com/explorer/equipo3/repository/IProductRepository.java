package com.explorer.equipo3.repository;

import com.explorer.equipo3.model.Detail;
import com.explorer.equipo3.model.Product;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    @Query("SELECT DISTINCT p FROM Product p " +
            "WHERE p.state = true OR p.state IS NULL")
    Page<Product> findAll2(Pageable pageable);

    @Transactional
    @Modifying
    @Query("UPDATE Product p SET p.state = false WHERE p.id = :idProduct "+
            "AND (p.state = true OR p.state IS NULL)")
    int cancelProduct(@Param("idProduct") Long idProduct);


    @Query("SELECT p FROM Product p WHERE p.category.id IN :category_id "+
            "AND (p.state = true OR p.state IS NULL)")
    List<Product> findByCategory_idIn(@Param("category_id") List<Long> category_id);

    @Query("SELECT p FROM Product p WHERE p.category.id in :category_id "+
            "AND (p.state = true OR p.state IS NULL)")
    Page<Product>findByCategory_idInPageable(@Param("category_id")List<Long>category_id,Pageable pageable);

    @Query("SELECT p FROM Product p WHERE p.name LIKE %:name% "+
            "AND (p.state = true OR p.state IS NULL)")
    Optional<Product> findByName(String name);


}
