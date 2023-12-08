package com.explorer.equipo3.controller;

import com.explorer.equipo3.model.City;
import com.explorer.equipo3.model.Country;
import com.explorer.equipo3.service.ICityService;
import com.explorer.equipo3.service.ICountryService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
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

    private static final Logger logger = LogManager.getLogger(CityController.class);

    @GetMapping
    public ResponseEntity<List<City>> getAllCities(){
        return ResponseEntity.ok(cityService.getAllCities());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCityById(@PathVariable Long id){
        logger.info("Ingresamos al metodo get city by id");
        Optional<City> citySearch = cityService.getCityById(id);
        if(citySearch.isPresent()){
            logger.info("city encontrada");
            return ResponseEntity.ok(citySearch.orElseThrow());
        }
        logger.info("la city no fue encontrada");
        return ResponseEntity.notFound().build();

    }

    @PostMapping("/create")
    public ResponseEntity<?> addCountry(@RequestBody City city){
        logger.info("city creada");
        return ResponseEntity.status(HttpStatus.CREATED).body(cityService.saveCity(city));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatecity(@PathVariable Long id, @RequestBody City city){
        logger.info("ingresamos al metodo de actualizar city");
        Optional<City> cityOptional = cityService.updateCity(id, city);
        if(cityOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCity(@PathVariable Long id){
        logger.info("ingresamos al metodo de eliminar city");
        Optional cityOptional = cityService.getCityById(id);
        if(cityOptional.isPresent()){
            logger.info("city encontrada");
            cityService.deleteCityById(id);
            return ResponseEntity.noContent().build();
        }
        logger.info("city no encontrada");
        return ResponseEntity.notFound().build();
    }

    @GetMapping("/country")
    public ResponseEntity<List<City>> findByProvince(@RequestParam Long countryId){
        logger.info("ingresamos al metodo de buscar city por country");
        Optional<Country>  countrySearch = countryService.getCountryById(countryId);
        if(countrySearch.isPresent()){
            logger.info("city encontrada");
            List<City> citySearch = cityService.getCitiesByCountry_id(countryId);
            return ResponseEntity.ok(citySearch);
        }
        logger.info("city no encontrada");
        return ResponseEntity.notFound().build();
    }


}
