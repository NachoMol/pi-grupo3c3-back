package com.explorer.equipo3.repository;


import com.explorer.equipo3.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ICityRepository extends JpaRepository<City, Long> {


    List<City> findByCountry_Id(Long country_Id);
}
