package com.explorer.equipo3.service;

import com.explorer.equipo3.controller.CityController;
import com.explorer.equipo3.controller.CountryController;
import com.explorer.equipo3.model.Country;
import com.explorer.equipo3.repository.ICountryRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CountryService implements ICountryService {

    @Autowired
    private ICountryRepository countryRepository;
    @Override
    public List<Country> getAllCountries() {
        return countryRepository.findAll();
    }

    @Override
    public Optional<Country> getCountryById(Long id) {
        return countryRepository.findById(id);
    }

    @Override
    public Country saveCountry(Country country) {
        return countryRepository.save(country);
    }

    @Override
    public Optional<Country> updateCountry(Long id, Country country) {
        Optional<Country> countryExist = countryRepository.findById(id);
        Country countryOptional = null;
        if (countryExist.isPresent()){
            Country countryDB = countryExist.orElseThrow();
            countryDB.setCountry(country.getCountry());
            countryOptional = countryRepository.save(countryDB);
        }
        return Optional.ofNullable(countryOptional);
    }

    @Override
    public void deleteCountryById(Long id) {
        countryRepository.deleteById(id);
    }
}
