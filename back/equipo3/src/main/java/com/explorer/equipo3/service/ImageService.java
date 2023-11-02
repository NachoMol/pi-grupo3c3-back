package com.explorer.equipo3.service;

import com.explorer.equipo3.model.Image;
import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.repository.IImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ImageService implements IImageService{

    @Autowired
    private IImageRepository imageRepository;

    @Override
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @Override
    public Optional<Image> getImageById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    public Image saveImage(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public Optional<Image> updateImage(Long id, Image image) {
        Optional<Image> imageExist = imageRepository.findById(id);
        Image imageOptional = null;
        if (imageExist.isPresent()){
            Image imageDB = imageExist.orElseThrow();
            imageDB.setUrl(image.getUrl());
            imageOptional = imageRepository.save(imageDB);
        }
        return Optional.ofNullable(imageOptional);
    }

    @Override
    public void deleteImageById(Long id) {

    }
}
