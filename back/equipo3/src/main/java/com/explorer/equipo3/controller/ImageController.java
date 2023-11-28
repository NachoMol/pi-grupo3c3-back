package com.explorer.equipo3.controller;
import com.explorer.equipo3.model.Image;
import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.service.IImageService;
import com.explorer.equipo3.service.IProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/images")
public class ImageController {

    @Autowired
    private IImageService imageService;

    @Autowired
    private IProductService productService;


    @GetMapping("/list")
    public ResponseEntity<List<Image>> getAllImages() {
        List<Image> images = imageService.getAllImages(); // Obtener todas las imágenes desde el repositorio
        return new ResponseEntity<>(images, HttpStatus.OK);
    }

    @GetMapping("/{id}/url")
    public ResponseEntity<String> getImageUrlById(@PathVariable Long id) {
        Optional<String> imageUrl = imageService.getImageUrlById(id);

        return imageUrl.map(url -> new ResponseEntity<>(url, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }

    @GetMapping("/id/{id}")
    public ResponseEntity<byte[]> getImageById(@PathVariable Long id) {
        Optional<byte[]> imageBytes = imageService.getImageBytesById(id);

        return imageBytes.map(bytes -> {
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG); // Ajusta según el tipo de archivo

            return new ResponseEntity<>(bytes, headers, HttpStatus.OK);
        }).orElseGet(() -> new ResponseEntity<>(HttpStatus.NO_CONTENT));
    }


    @PostMapping("/upload")
    public ResponseEntity<String> uploadImage(@RequestParam("file") MultipartFile file) throws Exception{
        return ResponseEntity.ok(imageService.uploadImage(file));
    }

    @PostMapping("/uploads")
    public ResponseEntity<String> uploadImages(@RequestParam("files") List<MultipartFile> files) throws Exception{
        List<String> uploadResults = new ArrayList<>();
        for (MultipartFile file : files) {
            try {
                String result = imageService.uploadImage(file);
                uploadResults.add(result);
            } catch (Exception e) {
                e.getMessage();
                uploadResults.add(e.getMessage());
            }
        }
        return ResponseEntity.ok(uploadResults.toString());
    }

}