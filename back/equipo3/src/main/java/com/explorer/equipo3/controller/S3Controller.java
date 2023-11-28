package com.explorer.equipo3.controller;

import com.explorer.equipo3.exception.ImageDownloadException;
import com.explorer.equipo3.exception.ImageUploadException;
import com.explorer.equipo3.model.vm.Asset;
import com.explorer.equipo3.service.IS3Service;
import com.explorer.equipo3.service.S3Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/s3")
public class S3Controller {

    @Autowired
    private IS3Service s3Service;

    @GetMapping("/download/{filename}")
    public String downloadFile(@PathVariable("filename") String filename)
            throws ImageDownloadException {
        return s3Service.downloadFile(filename);
    }

    @CrossOrigin(origins = "http://localhost:5173")
    @GetMapping("/imageUrl/{filename}")
    public String getImageUrl(@PathVariable("filename") String filename) {
        return s3Service.getImageUrl(filename);
    }

    @GetMapping("/list")
    public List<String> getAllObjects() throws IOException {
        return s3Service.listFiles();
    }

    @PostMapping("/upload")
    public String uploadFile(@RequestParam("file") MultipartFile file)
            throws ImageUploadException {
        return s3Service.uploadFile(file);
    }

    @PostMapping("/uploadMultiple")
    public String uploadMultipleFiles(@RequestParam("files") List<MultipartFile> files)
            throws ImageUploadException {
        return s3Service.uploadFiles(files);
    }

    @PutMapping("/rename/{oldFilename}/{newFilename}")
    public String updateFile(@PathVariable("oldFilename") String oldFilename,
                             @PathVariable("newFilename") String newFilename) throws IOException {
        return s3Service.renameFile(oldFilename, newFilename);
    }

    @PutMapping("/update/{oldFilename}")
    public String updateFile(@RequestParam("file") MultipartFile file,
                             @PathVariable("oldFilename") String oldFilename) throws IOException {
        return s3Service.updateFile(file, oldFilename);
    }

    @DeleteMapping("/delete/{filename}")
    public String deleteFile(@PathVariable("filename") String filename) throws IOException {
        return s3Service.deleteFile(filename);
    }


}
