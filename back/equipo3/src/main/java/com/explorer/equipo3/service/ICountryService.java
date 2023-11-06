package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Country;

import java.util.List;
import java.util.Optional;

public interface ICountryService {

    List<Country> getAllCountries();
    Optional<Country> getCountryById(Long id);
    Country saveCountry(Country country);
    Optional<Country> updateCountry(Long id, Country country);
    void deleteCountryById(Long id);
}
