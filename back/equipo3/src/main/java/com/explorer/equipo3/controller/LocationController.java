package com.explorer.equipo3.controller;

import com.explorer.equipo3.model.Location;
import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.service.ILocationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/locations")
public class LocationController {

    @Autowired
    private ILocationService locationService;

    @GetMapping
    public ResponseEntity<List<Location>> getAllLocations(){
        return ResponseEntity.ok(locationService.getAllLocations());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getLocationById(@PathVariable Long id){
        Optional<Location> locationSearch = locationService.getLocationById(id);
        if(locationSearch.isPresent()){
            return ResponseEntity.ok(locationSearch.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> addLocation(@RequestBody Location location){
        return ResponseEntity.status(HttpStatus.CREATED).body(locationService.saveLocation(location));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateLocation(@PathVariable Long id, @RequestBody Location location){
        Optional<Location> locationOptional = locationService.updateLocation(id, location);
        if(locationOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLocation(@PathVariable Long id){
        Optional locationOptional = locationService.getLocationById(id);
        if(locationOptional.isPresent()){
            locationService.deleteLocationById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
