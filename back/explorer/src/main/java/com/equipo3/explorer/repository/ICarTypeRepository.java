package com.equipo3.explorer.repository;

import com.equipo3.explorer.model.CarType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ICarTypeRepository extends JpaRepository<CarType, Long> {
}
