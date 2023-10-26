package com.equipo3.explorer.controller;

import com.equipo3.explorer.model.CarType;
import com.equipo3.explorer.model.Model;
import com.equipo3.explorer.service.ICarTypeService;
import com.equipo3.explorer.service.IModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("categories")
public class CarTypeController {

    @Autowired
    private ICarTypeService carTypeService;

    @GetMapping()
    public ResponseEntity<List<CarType>> getAllCarType(){
        return ResponseEntity.ok(carTypeService.getAllCarTypes());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCarTypeById(@PathVariable Long id){
        Optional<CarType> ctSearch = carTypeService.getCarTypeById(id);
        if(ctSearch.isPresent()){
            return ResponseEntity.ok(ctSearch.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> addCarType(@RequestBody CarType ct){
        return ResponseEntity.status(HttpStatus.CREATED).body(carTypeService.saveCarType(ct));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateCarType(@PathVariable Long id, @RequestBody CarType ct){
        Optional<CarType> ctOptional = carTypeService.updateCarType(id, ct);
        if(ctOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteCarType(@PathVariable Long id){
        Optional<CarType> ctOptional = carTypeService.getCarTypeById(id);
        if(ctOptional.isPresent()){
            carTypeService.deleteCarTypeById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
