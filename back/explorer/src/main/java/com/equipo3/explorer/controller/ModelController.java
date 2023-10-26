package com.equipo3.explorer.controller;

import com.equipo3.explorer.model.Car;
import com.equipo3.explorer.model.Model;
import com.equipo3.explorer.service.IModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("models")
public class ModelController {

    @Autowired
    private IModelService modelService;

    @GetMapping()
    public ResponseEntity<List<Model>> getAllModels(){
        return ResponseEntity.ok(modelService.getAllModels());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getModelById(@PathVariable Long id){
        Optional<Model> modelSearch = modelService.getModelById(id);
        if(modelSearch.isPresent()){
            return ResponseEntity.ok(modelSearch.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> addModel(@RequestBody Model model){
        return ResponseEntity.status(HttpStatus.CREATED).body(modelService.saveModel(model));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateModel(@PathVariable Long id, @RequestBody Model model){
        Optional<Model> modelOptional = modelService.updateModel(id, model);
        if(modelOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteModel(@PathVariable Long id){
        Optional<Model> modelOptional = modelService.getModelById(id);
        if(modelOptional.isPresent()){
            modelService.deleteModelById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
