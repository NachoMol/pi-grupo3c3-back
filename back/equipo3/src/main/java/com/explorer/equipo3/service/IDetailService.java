package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Category;
import com.explorer.equipo3.model.Detail;

import java.util.List;
import java.util.Optional;

public interface IDetailService {

    List<Detail> getAllDetails();
    Optional<Detail> getDetailById(Long id);
    Detail saveDetail(Detail detail);
    Optional<Detail> updateDetail(Long id, Detail detail);
    void deleteDetailById(Long id);
}
