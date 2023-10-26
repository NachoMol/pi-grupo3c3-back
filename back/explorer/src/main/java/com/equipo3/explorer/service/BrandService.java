package com.equipo3.explorer.service;

import com.equipo3.explorer.model.Brand;
import com.equipo3.explorer.repository.IBrandRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BrandService implements IBrandService{

    @Autowired
    private IBrandRepository brandRepository;

    @Override
    public List<Brand> getAllBrands() {
        return brandRepository.findAll();
    }

    @Override
    public Optional<Brand> getBrandById(Long id) {
        return brandRepository.findById(id);
    }

    @Override
    public Brand saveBrand(Brand brand) {
        return brandRepository.save(brand);
    }

    @Override
    public Optional<Brand> updateBrand(Long id, Brand brand) {
        Optional<Brand> brandExist = brandRepository.findById(id);
        Brand brandOptional = null;
        if(brandExist.isPresent()){
            Brand brandDB = brandExist.orElseThrow();
            brandDB.setName(brand.getName());
            brandOptional = brandRepository.save(brandDB);
        }
        return Optional.ofNullable(brandOptional);
    }

    @Override
    public void deleteBrandById(Long id) {
        brandRepository.deleteById(id);
    }
}
