package com.equipo3.explorer.service;

import com.equipo3.explorer.model.Car;
import com.equipo3.explorer.repository.ICarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class CarService implements ICarService{

    @Autowired
    private ICarRepository carRepository;

    @Override
    @Transactional
    public Car saveCar(Car car) {return carRepository.save(car); }



}
