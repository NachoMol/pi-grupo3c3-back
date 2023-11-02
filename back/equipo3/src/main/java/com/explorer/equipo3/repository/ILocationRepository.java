package com.explorer.equipo3.repository;

import com.explorer.equipo3.model.Location;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ILocationRepository extends JpaRepository<Location, Long> {
}
