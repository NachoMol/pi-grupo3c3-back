package com.explorer.equipo3.service;

import com.explorer.equipo3.exception.ImageDownloadException;
import com.explorer.equipo3.exception.ImageUploadException;
import com.explorer.equipo3.model.Image;
import com.explorer.equipo3.model.Product;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

public interface IS3Service {

    String getImageUrl(String filename);

    String uploadFile(MultipartFile file) throws ImageUploadException;

    String uploadFiles(List<MultipartFile> files) throws ImageUploadException;

    String downloadFile(String filename) throws ImageDownloadException;
    List<String> listFiles() throws IOException;
    String renameFile(String oldFilename, String newFilename) throws IOException;
    String updateFile(MultipartFile file, String oldFilename) throws IOException;
    String deleteFile(String filename) throws IOException;

    List<Image> getImagesByProduct(Product product);
}
