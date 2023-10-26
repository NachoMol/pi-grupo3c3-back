package com.equipo3.explorer.service;

import com.equipo3.explorer.model.CarType;
import com.equipo3.explorer.repository.ICarTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CarTypeService implements ICarTypeService{

    @Autowired
    private ICarTypeRepository carTypeRepository;
    @Override
    public List<CarType> getAllCarTypes() {
        return carTypeRepository.findAll();
    }

    @Override
    public Optional<CarType> getCarTypeById(Long id) {
        return carTypeRepository.findById(id);
    }

    @Override
    public CarType saveCarType(CarType ct) {
        return carTypeRepository.save(ct);
    }

    @Override
    public Optional<CarType> updateCarType(Long id, CarType ct) {
        Optional<CarType> ctExist = carTypeRepository.findById(id);
        CarType ctOptional = null;
        if(ctExist.isPresent()){
            CarType ctDB = ctExist.orElseThrow();
            ctDB.setName(ct.getName());
            ctOptional = carTypeRepository.save(ctDB);
        }
        return Optional.ofNullable(ctOptional);
    }

    @Override
    public void deleteCarTypeById(Long id) {
        carTypeRepository.deleteById(id);
    }
}
