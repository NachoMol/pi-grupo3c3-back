package com.equipo3.explorer.service;

import com.equipo3.explorer.model.Image;

import java.util.List;
import java.util.Optional;

public interface IImageService {

    List<Image> getAllImages();
    Optional<Image> getImageById(Long id);
    Image saveImage(Image img);
    Optional<Image> updateImage(Image img);
    void deleteImageById(Long id);
}
