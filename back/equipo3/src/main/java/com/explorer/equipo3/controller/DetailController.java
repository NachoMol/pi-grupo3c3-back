package com.explorer.equipo3.controller;

import com.explorer.equipo3.model.Category;
import com.explorer.equipo3.model.Detail;
import com.explorer.equipo3.service.IDetailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

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
}
