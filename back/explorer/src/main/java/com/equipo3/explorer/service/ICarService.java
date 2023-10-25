package com.equipo3.explorer.service;

import com.equipo3.explorer.model.Car;
import com.equipo3.explorer.model.Role;

import java.util.List;
import java.util.Optional;

public interface ICarService {

    List<Car> getAllCars();
    Optional<Car> getCarById(Long id);
    Car saveCar(Car car);
    Optional<Car> updateCar(Car car);
    void deleteCarById(Long id);

}
