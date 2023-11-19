package com.explorer.equipo3.controller;

import com.explorer.equipo3.model.Category;
import com.explorer.equipo3.model.Detail;
import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.service.DetailService;
import com.explorer.equipo3.service.IDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/details")
public class DetailController {

    @Autowired
    private IDetailService detailService;

    @GetMapping
    public ResponseEntity<List<Detail>> getAllDetail(){
        return ResponseEntity.ok(detailService.getAllDetails());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getDetailById(@PathVariable Long id){
        Optional<Detail> detailSearch = detailService.getDetailById(id);
        if(detailSearch.isPresent()){
            return ResponseEntity.ok(detailSearch.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> addDetail(@RequestBody Detail detail){
        return ResponseEntity.status(HttpStatus.CREATED).body(detailService.saveDetail(detail));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateDetail(@PathVariable Long id, @RequestBody Detail updatedDetail){
        Optional<Detail> detailOptional = detailService.getDetailById(id);
        if(detailOptional.isPresent()){
            Detail detail = detailOptional.get();

            // Actualiza solo los campos necesarios
            detail.setName(updatedDetail.getName());
            detail.setImg_url(updatedDetail.getImg_url());

            Detail savedDetail = detailService.saveDetail(detail);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedDetail);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteDetail(@PathVariable Long id){
        Optional detailOptional = detailService.getDetailById(id);
        if(detailOptional.isPresent()){
            detailService.deleteDetailById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}

