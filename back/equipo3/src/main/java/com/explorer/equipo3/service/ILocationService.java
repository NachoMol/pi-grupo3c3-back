package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Location;
import com.explorer.equipo3.model.Product;

import java.util.List;
import java.util.Optional;

public interface ILocationService {

    List<Location> getAllLocations();
    Optional<Location> getLocationById(Long id);
    Location saveLocation(Location location);
    Optional<Location> updateLocation(Long id, Location location);
    void deleteLocationById(Long id);
}
