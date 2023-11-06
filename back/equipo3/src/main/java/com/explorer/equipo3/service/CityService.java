package com.explorer.equipo3.service;

import com.explorer.equipo3.model.City;
import com.explorer.equipo3.repository.ICityRepository;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CityService implements ICityService{
    private ICityRepository cityRepository;
    @Override
    public List<City> getAllCities() {
        return null;
    }

    @Override
    public Optional<City> getCityById(Long id) {
        return Optional.empty();
    }

    @Override
    public City saveCity(City city) {
        return null;
    }

    @Override
    public Optional<City> updateCity(Long id, City city) {
        return Optional.empty();
    }

    @Override
    public void deleteCityById(Long id) {

    }
    @Override
    public List<City> getByProvince(Long id) {
        return cityRepository.findByProvince(id);
    }
}
