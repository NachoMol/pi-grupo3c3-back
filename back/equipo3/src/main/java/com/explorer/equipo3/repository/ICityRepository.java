package com.explorer.equipo3.repository;


import com.explorer.equipo3.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


@Repository
public interface ICityRepository extends JpaRepository<City, Long> {
    // consulta personalizada para recuperar City por province
    @Query("SELECT p FROM city p WHERE p.province_id = :provinceId")
    List<City> findByProvince(@Param("province_Id") Long provinceId);
}
