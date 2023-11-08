package com.explorer.equipo3.controller;

import com.explorer.equipo3.model.City;
import com.explorer.equipo3.model.Country;
import com.explorer.equipo3.service.ICityService;
import com.explorer.equipo3.service.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/cities")
public class CityController {
    @Autowired
    private ICityService cityService;

    @Autowired
    private ICountryService countryService;

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

    @GetMapping("/country")
    public ResponseEntity<List<City>> findByProvince(@RequestParam Long countryId){
        Optional<Country>  countrySearch = countryService.getCountryById(countryId);
        if(countrySearch.isPresent()){
            List<City> citySearch = cityService.getCitiesByCountry_id(countryId);
            return ResponseEntity.ok(citySearch);
        }
        return ResponseEntity.notFound().build();
    }


}
