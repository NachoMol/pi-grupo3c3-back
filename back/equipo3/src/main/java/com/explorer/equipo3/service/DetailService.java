package com.explorer.equipo3.service;

import com.explorer.equipo3.controller.DetailController;
import com.explorer.equipo3.model.Category;
import com.explorer.equipo3.model.Detail;
import com.explorer.equipo3.repository.IDetailRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class DetailService implements IDetailService{

    private static final Logger logger = LogManager.getLogger(DetailService.class);

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
        logger.info("buscamos el detail");
        Optional<Detail> detailExist = detailRepository.findById(id);
        Detail detailOptional = null;
        if (detailExist.isPresent()){
            logger.info("detail encontrado");
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
