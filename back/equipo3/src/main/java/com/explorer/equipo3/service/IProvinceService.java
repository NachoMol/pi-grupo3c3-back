package com.explorer.equipo3.service;


import com.explorer.equipo3.model.Province;

import java.util.List;
import java.util.Optional;

public interface IProvinceService {
    List<Province> getAllProvinces();
    Optional<Province> getProvinceById(Long id);
    Province saveProvince(Province location);
    Optional<Province> updateProvince(Long id, Province province);
    void deleteProvinceById(Long id);
     List<Province> getProvinceByCountry(Long id);
}
