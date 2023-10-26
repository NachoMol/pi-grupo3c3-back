package com.equipo3.explorer.controller;

import com.equipo3.explorer.model.Brand;
import com.equipo3.explorer.service.IBrandService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("brands")
public class BrandController {

    @Autowired
    private IBrandService brandService;

    @GetMapping()
    public ResponseEntity<List<Brand>> getAllBrands(){
        return ResponseEntity.ok(brandService.getAllBrands());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getBrandById(@PathVariable Long id){
        Optional<Brand> brandSearch = brandService.getBrandById(id);
        if(brandSearch.isPresent()){
            return ResponseEntity.ok(brandSearch.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> addBrand(@RequestBody Brand brand){
        return ResponseEntity.status(HttpStatus.CREATED).body(brandService.saveBrand(brand));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBrand(@PathVariable Long id, @RequestBody Brand brand){
        Optional<Brand> brandOptional = brandService.updateBrand(id, brand);
        if(brandOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete")
    public ResponseEntity<String> deleteBrandById(@PathVariable Long id){
        Optional<Brand> brandOptional = brandService.getBrandById(id);
        if(brandOptional.isPresent()){
            brandService.deleteBrandById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
