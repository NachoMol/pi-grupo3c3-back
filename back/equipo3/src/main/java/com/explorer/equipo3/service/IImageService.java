package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Image;
import com.explorer.equipo3.model.Product;

import java.util.List;
import java.util.Optional;

public interface IImageService {

    List<Image> getAllImages();
    Optional<Image> getImageById(Long id);
    Image saveImage(Image image);
    Optional<Image> updateImage(Long id, Image image);
    void deleteImageById(Long id);
}
