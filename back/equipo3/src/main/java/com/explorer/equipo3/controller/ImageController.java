package com.explorer.equipo3.controller;
import com.explorer.equipo3.model.Image;
import com.explorer.equipo3.service.IImageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
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


    @GetMapping("/list")
    public ResponseEntity<List<Image>> getAllImages() {
        List<Image> images = imageService.getAllImages(); // Obtener todas las imágenes desde el repositorio
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @GetMapping("/{id}/url")
    public ResponseEntity<String> getImageUrlById(@PathVariable Long id) {
        Optional<String> imageUrl = imageService.getImageUrlById(id);

        return imageUrl.map(url -> new ResponseEntity<>(url, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable Long id) {
        Optional<byte[]> imageBytes = imageService.getImageBytesById(id);

        return imageBytes.map(bytes -> {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Ajusta según el tipo de archivo

            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(MultipartFile file, String data) throws Exception{
        return ResponseEntity.ok(imageService.uploadImage(file, data));
    }

}