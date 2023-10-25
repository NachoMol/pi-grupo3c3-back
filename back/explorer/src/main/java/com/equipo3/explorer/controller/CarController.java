package com.equipo3.explorer.controller;


import com.equipo3.explorer.model.Car;
import com.equipo3.explorer.model.Role;
import com.equipo3.explorer.service.ICarService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("cars")
public class CarController {

    @Autowired
    private ICarService carService;

    @GetMapping()
    public ResponseEntity<List<Car>> getAllCars(){
        return ResponseEntity.ok(carService.getAllCars());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCarById(@PathVariable Long id){
        Optional<Car> carSearch = carService.getCarById(id);
        if(carSearch.isPresent()){
            return ResponseEntity.ok(carSearch.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<Car> addCar(@RequestBody Car car){
        return ResponseEntity.status(HttpStatus.CREATED).body(carService.saveCar(car));
    }

    @PutMapping("/update")
    public ResponseEntity<?> updateCar(@RequestBody Car car){
        Optional<Car> carOptional = carService.updateCar(car);
        if(carOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCar(@PathVariable Long id){
        Optional<Car> carOptional = carService.getCarById(id);
        if(carOptional.isPresent()){
            carService.deleteCarById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
