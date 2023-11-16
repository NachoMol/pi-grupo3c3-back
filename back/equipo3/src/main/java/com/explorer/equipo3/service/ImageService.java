package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Image;
import com.explorer.equipo3.repository.IImageRepository;
import org.apache.commons.io.IOUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService implements IImageService{

    @Autowired
    private IImageRepository imageRepository;


    @Override
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<String> getImageUrlById(Long id) {
        Optional<Image> optionalImage = imageRepository.findById(id);

        return optionalImage.map(Image::getUrl);
    }

    @Override
    public Optional<byte[]> getImageBytesById(Long id) {
        Optional<Image> optionalImage = imageRepository.findById(id);

        return optionalImage.map(image -> {
            try {
                URL imageUrl = new URL(image.getUrl());
                try (InputStream inputStream = imageUrl.openStream()) {
                    return IOUtils.toByteArray(inputStream);
                }
            } catch (IOException e) {
                e.printStackTrace(); // Manejo apropiado de la excepción
                return null;
            }
        });
    }


    @Override
    public Optional<Image> getImageByFilename(String filename) {
        return imageRepository.findByFilename(filename);
    }

    @Override
    public String uploadImage(MultipartFile imageFile, String data) throws Exception {
        try {
            String filename = UUID.randomUUID().toString();
            byte[] bytes = imageFile.getBytes();
            String fileOriginalName = imageFile.getOriginalFilename();

            long fileSize = imageFile.getSize();
            long maxSize = 10485760; // 10MB

            if (fileSize > maxSize) {
                return "File size too large. Max size is 10MB.";
            }

            if(!fileOriginalName.endsWith(".jpg") && !fileOriginalName.endsWith(".jpeg") && !fileOriginalName.endsWith(".png")) {
                return "File type not supported. Only JPG, JPEG and PNG files are allowed.";
            }

            String fileExtension = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
            String newFilename = filename + fileExtension;

            Path folderPath = Paths.get("src/main/resources/static/images");
            Files.createDirectories(folderPath);

            // Modificación para incluir la URL completa
            String imageUrl = "http://localhost:8080/images/" + newFilename;


            // Guardar información en la base de datos
            Image image = new Image(newFilename, imageUrl, data); // Ajusta según tus necesidades
            imageRepository.save(image);

            Path filePath = folderPath.resolve(newFilename);
            Files.write(filePath, bytes);

            return "Image uploaded successfully.";

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }


}
