package com.explorer.equipo3.controller;

import com.explorer.equipo3.model.City;
import com.explorer.equipo3.model.Province;
import com.explorer.equipo3.repository.IProvinceRepository;
import com.explorer.equipo3.service.ICityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/city")
public class CityController {
    @Autowired
    private ICityService cityService;
    @Autowired
    private IProvinceRepository provinceRepository;

    @GetMapping
    public ResponseEntity<List<City>> getAllCities(){
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCityById(@PathVariable Long id){
        Optional<City> citySearch = cityService.getCityById(id);
        if(citySearch.isPresent()){
            return ResponseEntity.ok(citySearch.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> addCountry(@RequestBody City city){
        return ResponseEntity.status(HttpStatus.CREATED).body(cityService.saveCity(city));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatecity(@PathVariable Long id, @RequestBody City city){
        Optional<City> cityOptional = cityService.updateCity(id, city);
        if(cityOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCity(@PathVariable Long id){
        Optional cityOptional = cityService.getCityById(id);
        if(cityOptional.isPresent()){
            cityService.deleteCityById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/province/{id}")
    public ResponseEntity<List<City>> findByProvince(@PathVariable Long provinceId){
        Optional<Province>  provinceSearch = provinceRepository.findById(provinceId);
        if(provinceSearch.isPresent()){
            List<City> citySearch = cityService.getByProvince(provinceId);
            return ResponseEntity.ok(citySearch);
        }
        return ResponseEntity.notFound().build();
    }


}
