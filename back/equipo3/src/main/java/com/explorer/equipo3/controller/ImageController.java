package com.explorer.equipo3.controller;

import com.explorer.equipo3.model.Image;
import com.explorer.equipo3.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private IImageService imageService;

    @Autowired
    private MediaController mediaController;

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




    @PostMapping("/upload")
    public ResponseEntity<?> addImage(@RequestParam("file") List<MultipartFile> files,Image image ){
        List<String> urls = mediaController.uploadFile(files);
        try {
            if (!urls.isEmpty()){
                image.setUrl(String.valueOf(urls));
                return ResponseEntity.status(HttpStatus.CREATED).body(imageService.saveImage(image));
            }else{
                return ResponseEntity.notFound().build();
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
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
