package com.explorer.equipo3.service;

import com.explorer.equipo3.exception.ImageUploadException;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ImageService implements IImageService{

    @Autowired
    private IImageRepository imageRepository;

    @Autowired
    private IProductService productService;


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
            String filename = imageFile.getOriginalFilename().replaceAll("[^a-zA-Z0-9.-]", "_");
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
            String newFilename = filename.endsWith(fileExtension) ? filename : filename + fileExtension;

            Path folderPath = Paths.get("src/main/resources/static/images");
            Files.createDirectories(folderPath);

            // Modificación para incluir la URL completa
            String imageUrl = "http://localhost:8080/images/" + newFilename;
            System.out.println(imageUrl);


            // Guardar información en la base de datos
            Image image = new Image();// Asigna los bytes de la imagen al campo 'data'
            image.setFilename(newFilename);
            image.setUrl(imageUrl);
            image.setTitle(data);
            image.setData(bytes);
            imageRepository.save(image);

            Path filePath = folderPath.resolve(newFilename);
            Files.write(filePath, bytes);

            return "Image uploaded successfully.";

        } catch (ImageUploadException e) {
            throw new ImageUploadException("Error uploading image");
        }
    }

    @Override
    public String uploadImages(List<MultipartFile> imageFiles, String data) throws Exception {
        List<String> uploadResults = new ArrayList<>();

        for (MultipartFile imageFile : imageFiles) {
            try {
                String filename = imageFile.getOriginalFilename().replaceAll("[^a-zA-Z0-9.-]", "_");
                byte[] bytes = imageFile.getBytes();
                String fileOriginalName = imageFile.getOriginalFilename();

                long fileSize = imageFile.getSize();
                long maxSize = 10485760; // 10MB

                if (fileSize > maxSize) {
                    uploadResults.add("File size too large. Max size is 10MB.");
                    continue; // Skip current file and proceed to the next one
                }

                if (!fileOriginalName.endsWith(".jpg") && !fileOriginalName.endsWith(".jpeg") && !fileOriginalName.endsWith(".png")) {
                    uploadResults.add("File type not supported. Only JPG, JPEG, and PNG files are allowed.");
                    continue; // Skip current file and proceed to the next one
                }

                String fileExtension = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
                String newFilename = filename.endsWith(fileExtension) ? filename : filename + fileExtension;


                Path folderPath = Paths.get("src/main/resources/static/images");
                Files.createDirectories(folderPath);

                // Modificación para incluir la URL completa
                String imageUrl = "http://localhost:8080/images/" + newFilename;

                // Guardar información en la base de datos
                Image image = new Image();
                image.setFilename(newFilename);
                image.setUrl(imageUrl);
                image.setTitle(data);
                image.setData(bytes);
                imageRepository.save(image);

                Path filePath = folderPath.resolve(newFilename);
                Files.write(filePath, bytes);

                uploadResults.add("Image uploaded successfully: " + imageUrl);
            } catch (IOException | ImageUploadException e) {
                // Manejo de excepciones
                e.printStackTrace();
                uploadResults.add("Error uploading image.");
            }
        }

        return uploadResults.toString();
    }



}
