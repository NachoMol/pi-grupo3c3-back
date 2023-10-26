package com.equipo3.explorer.repository;

import com.equipo3.explorer.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IImageRepository extends JpaRepository<Image,Long> {
}
