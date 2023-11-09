package com.explorer.equipo3.repository;

import com.explorer.equipo3.model.Detail;
import com.explorer.equipo3.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.List;
import java.util.Optional;

@Repository
public interface IProductRepository extends JpaRepository<Product, Long> {

    List<Product> findByCategory_idIn(List<Long> category_id);
    Optional<Product> findByName(String name);
}
