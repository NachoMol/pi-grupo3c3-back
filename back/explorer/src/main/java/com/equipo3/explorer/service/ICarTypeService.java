package com.equipo3.explorer.service;

import com.equipo3.explorer.model.CarType;
import com.equipo3.explorer.model.Reservation;

import java.util.List;
import java.util.Optional;

public interface ICarTypeService {

    List<CarType> getAllCarTypes();
    Optional<CarType> getCarTypeById(Long id);
    CarType saveCarType(CarType ct);
    Optional<CarType> updateCarType(Long id, CarType ct);
    void deleteCarTypeById(Long id);
}
