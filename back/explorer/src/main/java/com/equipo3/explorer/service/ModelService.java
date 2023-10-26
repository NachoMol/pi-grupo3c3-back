package com.equipo3.explorer.service;

import com.equipo3.explorer.model.Model;
import com.equipo3.explorer.repository.IModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
public class ModelService implements IModelService{

    @Autowired
    private IModelRepository modelRepository;


    @Override
    @Transactional(readOnly = true)
    public List<Model> getAllModels() {
        return modelRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Model> getModelById(Long id) {
        return modelRepository.findById(id);
    }

    @Override
    @Transactional
    public Model saveModel(Model model) {
        return modelRepository.save(model);
    }

    @Override
    @Transactional
    public Optional<Model> updateModel(Long id, Model model) {
        Optional<Model> modelExist = modelRepository.findById(id);
        Model modelOptional = null;
        if(modelExist.isPresent()){
            Model modelDB = modelExist.orElseThrow();
            modelDB.setName(model.getName());
            modelOptional = modelRepository.save(modelDB);
        }
        return Optional.ofNullable(modelOptional);
    }

    @Override
    @Transactional
    public void deleteModelById(Long id) {
        modelRepository.deleteById(id);
    }
}
