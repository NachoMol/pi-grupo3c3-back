package com.explorer.equipo3.service;


import com.explorer.equipo3.model.Image;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;


public interface IImageService {

    List<Image> getAllImages();

    String uploadImage(MultipartFile imageFile, String data) throws Exception;

    Optional<Image> getImageByFilename(String filename);

    Optional<String> getImageUrlById(Long id);

    Optional<byte[]> getImageBytesById(Long id);

    String uploadImages(List<MultipartFile> imageFiles, String data) throws Exception;
}
