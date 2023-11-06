package com.explorer.equipo3.repository;

import com.explorer.equipo3.model.Province;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface IProvinceRepository extends JpaRepository<Province, Long> {
    // consulta personalizada para recuperar province por country
    @Query("SELECT p FROM province p WHERE p.country_id = :countryId")
    List<Province> findByCountry(@Param("country_Id") Long countryId);


}
