package com.explorer.equipo3.service;


import com.explorer.equipo3.model.Image;
import com.explorer.equipo3.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


public interface IImageService {

    List<Image> getAllImages();

    Optional<Image> getImageByFilename(String filename);

    Optional<String> getImageUrlById(Long id);

    Optional<byte[]> getImageBytesById(Long id);

    String uploadImage(MultipartFile imageFile) throws Exception;

    String uploadImages(List<MultipartFile> imageFiles) throws Exception;

    List<Image> getImagesByProduct(Product product);
}
