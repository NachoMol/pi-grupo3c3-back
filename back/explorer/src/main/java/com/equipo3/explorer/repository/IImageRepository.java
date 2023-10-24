package com.equipo3.explorer.repository;

import com.equipo3.explorer.model.Image;
import org.springframework.data.jpa.repository.JpaRepository;

public interface IImageRepository extends JpaRepository<Image,Long> {
}
