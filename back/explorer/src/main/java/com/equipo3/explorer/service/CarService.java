package com.equipo3.explorer.service;

import com.equipo3.explorer.model.Car;
import com.equipo3.explorer.model.Role;
import com.equipo3.explorer.repository.ICarRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class CarService implements ICarService{

    @Autowired
    private ICarRepository carRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Car> getAllCars() {
        return carRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Car> getCarById(Long id) {
        return carRepository.findById(id);
    }

    @Override
    @Transactional
    public Car saveCar(Car car) {return carRepository.save(car); }

    @Override
    @Transactional
    public Optional<Car> updateCar(Car car) {
        Optional<Car> carExists = carRepository.findById(car.getId());
        Car carOptional = null;
        if(carExists.isPresent()){
            Car carDB = carExists.orElseThrow();
            carDB.setPrice(car.getPrice());
            carOptional = carRepository.save(carDB);
        }
        return Optional.ofNullable(carOptional);
    }

    @Override
    public void deleteCarById(Long id) {
        carRepository.deleteById(id);
    }


}
