package com.explorer.equipo3.controller;

import com.explorer.equipo3.model.Country;
import com.explorer.equipo3.service.ICountryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/countries")
public class CountryController {

    @Autowired
    private ICountryService countryService;

    @GetMapping
    public ResponseEntity<List<Country>> getAllCountry(){
        return ResponseEntity.ok(countryService.getAllCountries());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getCountryById(@PathVariable Long id){
        Optional<Country> countrySearch = countryService.getCountryById(id);
        if(countrySearch.isPresent()){
            return ResponseEntity.ok(countrySearch.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> addCountry(@RequestBody Country country){
        return ResponseEntity.status(HttpStatus.CREATED).body(countryService.saveCountry(country));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updatecountry(@PathVariable Long id, @RequestBody Country country){
        Optional<Country> countryOptional = countryService.updateCountry(id, country);
        if(countryOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteCountry(@PathVariable Long id){
        Optional countryOptional = countryService.getCountryById(id);
        if(countryOptional.isPresent()){
            countryService.deleteCountryById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
