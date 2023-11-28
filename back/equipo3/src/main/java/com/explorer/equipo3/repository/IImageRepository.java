package com.explorer.equipo3.repository;

import com.explorer.equipo3.model.Image;
import com.explorer.equipo3.model.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IImageRepository extends JpaRepository<Image, Long> {
    Optional<Image> findByFilename(String filename);
    List<Image> findByProduct(Product product);


}
