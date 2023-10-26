package com.equipo3.explorer.service;

import com.equipo3.explorer.model.Model;

import java.util.List;
import java.util.Optional;

public interface IModelService {

    List<Model> getAllModels();
    Optional<Model> getModelById(Long id);
    Model saveModel(Model model);
    Optional<Model> updateModel(Long id, Model model);
    void deleteModelById(Long id);

}
