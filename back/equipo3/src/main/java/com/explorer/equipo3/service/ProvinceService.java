package com.explorer.equipo3.service;
import com.explorer.equipo3.model.Province;
import com.explorer.equipo3.repository.IProvinceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ProvinceService implements IProvinceService{
    @Autowired
    private IProvinceRepository provinceRepository;
    @Override
    public List<Province> getAllProvinces() {
        return null;
    }

    @Override
    public Optional<Province> getProvinceById(Long id) {
        return Optional.empty();
    }

    @Override
    public Province saveProvince(Province location) {
        return null;
    }

    @Override
    public Optional<Province> updateProvince(Long id, Province province) {
        return Optional.empty();
    }

    @Override
    public void deleteProvinceById(Long id) {

    }
    @Override
    public List<Province> getProvinceByCountry (Long countryId){
        return provinceRepository.findByCountry(countryId);
    }
}
