package com.explorer.equipo3.controller;

import com.explorer.equipo3.model.Country;
import com.explorer.equipo3.model.Province;
import com.explorer.equipo3.repository.ICountryRepository;
import com.explorer.equipo3.service.ICountryService;
import com.explorer.equipo3.service.IProvinceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/province")
public class ProvinceController {

    @Autowired
    private IProvinceService provinceService;
    @Autowired
    private ICountryRepository countryRepository;

    @GetMapping
    public ResponseEntity<List<Province>> getAllProvinces(){
        return ResponseEntity.ok(provinceService.getAllProvinces());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getProvinceById(@PathVariable Long id){
        Optional<Province> provinceSearch = provinceService.getProvinceById(id);
        if(provinceSearch.isPresent()){
            return ResponseEntity.ok(provinceSearch.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    //find by Country
    @GetMapping("/country/{id}")
    public ResponseEntity<List<Province>> findByCountry(@PathVariable Long countryId){
        Optional<Country>  countrySearch = countryRepository.findById(countryId);
        if(countrySearch.isPresent()){
            List<Province> provinceSearch = provinceService.getProvinceByCountry(countryId);
                return ResponseEntity.ok(provinceSearch);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> addProvince(@RequestBody Province province){

        return ResponseEntity.status(HttpStatus.CREATED).body(provinceService.saveProvince(province));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProvince(@PathVariable Long id, @RequestBody Province province){
        Optional<Province> provinceOptional = provinceService.updateProvince(id, province);
        if(provinceOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteProvince(@PathVariable Long id){
        Optional provinceOptional = provinceService.getProvinceById(id);
        if(provinceOptional.isPresent()){
            provinceService.deleteProvinceById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }

}
