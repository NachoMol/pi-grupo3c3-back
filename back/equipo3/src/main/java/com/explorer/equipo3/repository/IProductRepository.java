package com.explorer.equipo3.repository;

import com.explorer.equipo3.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {
    @Query("SELECT p FROM Producto p JOIN p.category c WHERE c.id IN :categoryIds")
    List<Product> findByCategories(@Param("categoryIds") List<Long> categoryIds);
}
