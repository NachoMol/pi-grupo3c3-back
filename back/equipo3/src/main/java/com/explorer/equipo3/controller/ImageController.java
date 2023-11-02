package com.explorer.equipo3.controller;

import com.explorer.equipo3.model.Image;
import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.service.IImageService;
import com.explorer.equipo3.service.ImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private IImageService imageService;

    @GetMapping
    public ResponseEntity<List<Image>> getAllImages(){
        return ResponseEntity.ok(imageService.getAllImages());
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getImageById(@PathVariable Long id){
        Optional<Image> imageSearch = imageService.getImageById(id);
        if(imageSearch.isPresent()){
            return ResponseEntity.ok(imageSearch.orElseThrow());
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping("/create")
    public ResponseEntity<?> addImage(@RequestBody Image image){
        return ResponseEntity.status(HttpStatus.CREATED).body(imageService.saveImage(image));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateImage(@PathVariable Long id, @RequestBody Image image){
        Optional<Image> imageOptional = imageService.updateImage(id, image);
        if(imageOptional.isPresent()){
            return ResponseEntity.status(HttpStatus.CREATED).build();
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteImage(@PathVariable Long id){
        Optional imageOptional = imageService.getImageById(id);
        if(imageOptional.isPresent()){
            imageService.deleteImageById(id);
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
