package com.explorer.equipo3.service;

import com.explorer.equipo3.controller.CategoryController;
import com.explorer.equipo3.exception.ImageUploadException;
import com.explorer.equipo3.model.Image;
import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.repository.IImageRepository;
import org.apache.commons.io.IOUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.services.s3.model.S3Exception;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService implements IImageService{


    private static final Logger logger = LogManager.getLogger(ImageService.class);

    @Autowired
    private IImageRepository imageRepository;

    @Autowired
    private IProductService productService;

    @Autowired
    private IS3Service s3Service;


    @Override
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<String> getImageUrlById(Long id) {
        Optional<Image> optionalImage = imageRepository.findById(id);
        logger.info("image encontrada, devolvemos la url");
        return optionalImage.map(Image::getUrl);
    }

    @Override
    public Optional<byte[]> getImageBytesById(Long id) {
        Optional<Image> optionalImage = imageRepository.findById(id);
        return optionalImage.map(image -> {
            try {
                URL imageUrl = new URL(image.getUrl());
                try (InputStream inputStream = imageUrl.openStream()) {
                    logger.info("imagen encontrada,devolvemos array de bytes");
                    return IOUtils.toByteArray(inputStream);
                }
            } catch (IOException e) {
                logger.info("imagen no encontrada, error");
                e.printStackTrace(); // Manejo apropiado de la excepción
                return null;
            }
        });
    }

    @Override
    public List<Image> getImagesByProduct(Product product) {
        List<Image> localImages = imageRepository.findByProduct(product);
        List<Image> s3Images = s3Service.getImagesByProduct(product); // Necesitas implementar este método en tu servicio de S3

        // Combina las imágenes locales con las imágenes de S3
        List<Image> allImages = new ArrayList<>();
        allImages.addAll(localImages);
        allImages.addAll(s3Images);

        return allImages;
    }



    @Override
    public Optional<Image> getImageByFilename(String filename) {
        return imageRepository.findByFilename(filename);
    }

    @Override
    public String uploadImage(MultipartFile imageFile) throws Exception {
        try {
            logger.info("guardamos la image");
            String filename = imageFile.getOriginalFilename().replaceAll("[^a-zA-Z0-9.-]", "_");
            String fileOriginalName = imageFile.getOriginalFilename();

            long fileSize = imageFile.getSize();
            long maxSize = 10485760; // 10MB

            if (fileSize > maxSize) {
                return "File size too large. Max size is 10MB.";
            }

            if (!fileOriginalName.endsWith(".jpg") && !fileOriginalName.endsWith(".jpeg") && !fileOriginalName.endsWith(".png")) {
                return "File type not supported. Only JPG, JPEG, and PNG files are allowed.";
            }

            String fileExtension = fileOriginalName.substring(fileOriginalName.lastIndexOf("."));
            String newFilename = filename.endsWith(fileExtension) ? filename : filename + fileExtension;

            // Modificación para incluir la URL completa de S3
            String imageUrl = "https://s3.amazonaws.com/bucket-explorer-images/" + newFilename;
            System.out.println(imageUrl);

            /*// Guardar información en la base de datos
            Image image = new Image();
            image.setFilename(newFilename);
            image.setUrl(imageUrl);
            imageRepository.save(image);*/

            // Subir la imagen al bucket de S3
            String uploadResult = s3Service.uploadFile(imageFile);

            // Guardar información en la base de datos solo si la subida a S3 es exitosa
            if ("Image uploaded successfully".equals(uploadResult)) {
                Image image = new Image();
                image.setFilename(newFilename);
                image.setUrl(imageUrl);
                imageRepository.save(image);
                return "Image uploaded successfully.";
            } else {
                return "Error uploading image.";
            }
        } catch (ImageUploadException e) {
            throw new ImageUploadException("Error uploading image");
        }
    }


    @Override
    public String uploadImages(List<MultipartFile> imageFiles) throws Exception {
        List<String> uploadResults = new ArrayList<>();

        for (MultipartFile imageFile : imageFiles) {
            try {
                String filename = imageFile.getOriginalFilename().replaceAll("[^a-zA-Z0-9.-]", "_");
                String fileOriginalName = imageFile.getOriginalFilename();
                String uploadResult = s3Service.uploadFile(imageFile);

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

                // Modificación para incluir la URL completa desde S3
                String imageUrl = "https://s3.amazonaws.com/bucket-explorer-images/" + newFilename;

                /*// Guardar información en la base de datos
                Image image = new Image();
                image.setFilename(newFilename);
                image.setUrl(imageUrl);
                imageRepository.save(image);

                uploadResults.add("Image uploaded successfully: " + uploadResult);*/

                // Guardar información en la base de datos solo si la subida a S3 es exitosa
                if ("Image uploaded successfully".equals(uploadResult)) {
                    Image image = new Image();
                    image.setFilename(newFilename);
                    image.setUrl(imageUrl);
                    imageRepository.save(image);

                    uploadResults.add("Image uploaded successfully: " + uploadResult);
                } else {
                    uploadResults.add("Error uploading image.");
                }

            } catch (S3Exception | ImageUploadException e) {
                // Manejo de excepciones
                e.printStackTrace();
                uploadResults.add("Error uploading image.");
            }
        }

        return uploadResults.toString();
    }




}
