package com.equipo3.explorer.repository;

import com.equipo3.explorer.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ICarRepository extends JpaRepository<Car,Long> {
}
