package com.equipo3.explorer.service;

import com.equipo3.explorer.model.Image;
import com.equipo3.explorer.repository.IImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class ImageService implements IImageService {

    @Autowired
    private IImageRepository imageRepository;

    @Override
    @Transactional(readOnly = true)
    public List<Image> getAllImages() {
        return imageRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<Image> getImageById(Long id) {
        return imageRepository.findById(id);
    }

    @Override
    @Transactional
    public Image saveImage(Image img) {
        return imageRepository.save(img);
    }

    @Override
    @Transactional
    public Optional<Image> updateImage(Image img) {
        Optional<Image> imgExist = imageRepository.findById(img.getId());
        Image imgOptional = null;
        if(imgExist.isPresent()){
            Image imgDB = imgExist.orElseThrow();
            imgDB.setUrl(img.getUrl());
            imgOptional = imageRepository.save(imgDB);
        }
        return Optional.ofNullable(imgOptional);
    }

    @Override
    @Transactional
    public void deleteImageById(Long id) {
        imageRepository.deleteById(id);
    }
}
