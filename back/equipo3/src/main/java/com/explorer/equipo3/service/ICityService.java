package com.explorer.equipo3.service;

import com.explorer.equipo3.model.City;


import java.util.List;
import java.util.Optional;

public interface ICityService {

        List<City> getAllCities();
        Optional<City> getCityById(Long id);
        City saveCity(City location);
        Optional<City> updateCity(Long id, City city);
        void deleteCityById(Long id);
        List<City> getCitiesByCountry_id(Long country_id);

}
