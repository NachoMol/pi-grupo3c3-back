package com.equipo3.explorer.service;

import com.equipo3.explorer.model.Brand;
import com.equipo3.explorer.model.Car;

import java.util.List;
import java.util.Optional;

public interface IBrandService {

    List<Brand> getAllBrands();
    Optional<Brand> getBrandById(Long id);
    Brand saveBrand(Brand brand);
    Optional<Brand> updateBrand(Long id, Brand brand);
    void deleteBrandById(Long id);
}
