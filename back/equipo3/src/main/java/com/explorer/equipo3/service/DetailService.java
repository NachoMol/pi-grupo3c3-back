package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Category;
import com.explorer.equipo3.model.Detail;
import com.explorer.equipo3.repository.IDetailRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DetailService implements IDetailService{

    @Autowired
    private IDetailRepository detailRepository;

    @Override
    public List<Detail> getAllDetails() {
        return detailRepository.findAll();
    }

    @Override
    public Optional<Detail> getDetailById(Long id) {
        return detailRepository.findById(id);
    }

    @Override
    public Detail saveDetail(Detail detail) {
        return detailRepository.save(detail);
    }

    @Override
    public Optional<Detail> updateDetail(Long id, Detail detail) {
        Optional<Detail> detailExist = detailRepository.findById(id);
        Detail detailOptional = null;
        if (detailExist.isPresent()){
            Detail detailDB = detailExist.orElseThrow();
            detailDB.setName(detail.getName());
            detailOptional = detailRepository.save(detailDB);
        }
        return Optional.ofNullable(detailOptional);
    }

    @Override
    public void deleteDetailById(Long id) {
        detailRepository.deleteById(id);
    }
}
