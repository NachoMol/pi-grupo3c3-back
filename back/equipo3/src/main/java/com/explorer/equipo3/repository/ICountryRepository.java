package com.explorer.equipo3.repository;

import com.explorer.equipo3.model.Country;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface ICountryRepository extends JpaRepository<Country, Long> {

}
