package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Category;
import com.explorer.equipo3.model.City;
import com.explorer.equipo3.repository.ICityRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class CityService implements ICityService{
    @Autowired
    private ICityRepository cityRepository;

    @Override
    public List<City> getAllCities() {
        return cityRepository.findAll();
    }

    @Override
    public Optional<City> getCityById(Long id) {
        return cityRepository.findById(id);
    }

    @Override
    public City saveCity(City city) {
        return cityRepository.save(city);
    }

    @Override
    public Optional<City> updateCity(Long id, City city) {
        Optional<City> cityExist = cityRepository.findById(id);
        City cityOptional = null;
        if (cityExist.isPresent()){
            City cityDB = cityExist.orElseThrow();
            cityDB.setCity(city.getCity());
            cityOptional = cityRepository.save(cityDB);
        }
        return Optional.ofNullable(cityOptional);

    }


    @Override
    public void deleteCityById(Long id) {

        cityRepository.deleteById(id);
    }

    @Override
    public List<City> getCitiesByCountry_id(Long country_id) {
        return cityRepository.findByCountry_Id(country_id);
    }

}
