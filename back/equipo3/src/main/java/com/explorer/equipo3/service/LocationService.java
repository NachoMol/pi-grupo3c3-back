package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Location;
import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.repository.IImageRepository;
import com.explorer.equipo3.repository.ILocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService implements ILocationService{

    @Autowired
    private ILocationRepository locationRepository;
    @Override
    public List<Location> getAllLocations() {
        return locationRepository.findAll();
    }

    @Override
    public Optional<Location> getLocationById(Long id) {
        return locationRepository.findById(id);
    }

    @Override
    public Location saveLocation(Location location) {
        return locationRepository.save(location);
    }

    @Override
    public Optional<Location> updateLocation(Long id, Location location) {
        Optional<Location> locationExist = locationRepository.findById(id);
        Location locationOptional = null;
        if (locationExist.isPresent()){
            Location locationDB = locationExist.orElseThrow();
            locationDB.setCity(location.getCity());
            locationDB.setProvince(location.getProvince());
            locationDB.setCountry(location.getCountry());
            locationOptional = locationRepository.save(locationDB);
        }
        return Optional.ofNullable(locationOptional);
    }

    @Override
    public void deleteLocationById(Long id) {
        locationRepository.deleteById(id);
    }
}
