package com.explorer.equipo3.service;

import com.explorer.equipo3.exception.ImageDownloadException;
import com.explorer.equipo3.exception.ImageUploadException;
import com.explorer.equipo3.model.Image;
import com.explorer.equipo3.model.Product;
import com.explorer.equipo3.repository.IImageRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import software.amazon.awssdk.core.ResponseInputStream;
import software.amazon.awssdk.core.sync.RequestBody;
import software.amazon.awssdk.services.s3.S3Client;
import software.amazon.awssdk.services.s3.model.*;

import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class S3Service implements IS3Service{

    private final S3Client s3Client;

    @Autowired
    private IImageRepository imageRepository;

    @Autowired
    public S3Service(S3Client s3Client) {
        this.s3Client = s3Client;
    }

    @Override
    public String getImageUrl(String filename) {
        String bucketName = "bucket-explorer-images";
        String region = "us-east-1"; // reemplaza esto con la región de tu bucket
        return "https://" + bucketName + ".s3." + region + ".amazonaws.com/" + filename;
    }

    @Override
    public String uploadFile(MultipartFile file) throws ImageUploadException {
        try {
            String fileName = file.getOriginalFilename();
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket("bucket-explorer-images")
                    .key(fileName)
                    .contentType("image/jpeg")
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            return "Image uploaded successfully";
        } catch (Exception e) {
            throw new ImageUploadException(e.getMessage());
        }
    }

    @Override
    public String uploadFiles(List<MultipartFile> files) throws ImageUploadException {
        try {
            for (MultipartFile file : files) {
                String fileName = file.getOriginalFilename();
                PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                        .bucket("bucket-explorer-images")
                        .key(fileName)
                        .contentType("image/jpeg")
                        .build();
                s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            }
            return "Images uploaded successfully";
        } catch (Exception e) {
            throw new ImageUploadException(e.getMessage());
        }
    }


    @Override
    public String downloadFile(String filename) throws ImageDownloadException{

        String localPath = "/Users/" + System.getProperty("user.name") + "/Downloads/";

        if (!doesObjectExist(filename)) {
            throw new ImageDownloadException("Image not exist");
        }
        GetObjectRequest request = GetObjectRequest.builder()
                .bucket("bucket-explorer-images")
                .key(filename)
                .build();
        ResponseInputStream<GetObjectResponse> response = s3Client.getObject(request);
        try (FileOutputStream fos = new FileOutputStream(localPath + filename)) {
            byte[] buffer = new byte[1024];
            int bytesRead = 0;

            while ((bytesRead = response.read(buffer)) > 0) {
                fos.write(buffer, 0, bytesRead);
            }
        } catch (ImageDownloadException e) {
            throw new ImageDownloadException(e.getMessage());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return "Image downloaded successfully";
    }

    @Override
    public List<String> listFiles() throws IOException {
        try{
            ListObjectsRequest listObjectsRequest = ListObjectsRequest.builder()
                    .bucket("bucket-explorer-images")
                    .build();

            List<S3Object> objects = s3Client.listObjects(listObjectsRequest).contents();
            List<String> filenames = new ArrayList<>();

            for (S3Object object : objects) {
                filenames.add(object.key());
            }
            return filenames;
        }catch (S3Exception e){
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public String renameFile(String oldFilename, String newFilename) throws IOException {
        if (!doesObjectExist(oldFilename)) {
            throw new ImageDownloadException("Image not exist");
        }
        try{
            CopyObjectRequest copyObjectRequest = CopyObjectRequest.builder()
                    .copySource("bucket-explorer-images/" + oldFilename)
                    .destinationBucket("bucket-explorer-images")
                    .destinationKey(newFilename)
                    .build();
            s3Client.copyObject(copyObjectRequest);
            deleteFile(oldFilename);
            return "Image renamed successfully to " + newFilename;
        }catch (S3Exception e){
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public String updateFile(MultipartFile file,String oldFilename) throws IOException {
        if (!doesObjectExist(oldFilename)) {
            throw new ImageDownloadException("Image not exist");
        }
        try{
            String newFilename = file.getOriginalFilename();
            deleteFile(oldFilename);
            PutObjectRequest putObjectRequest = PutObjectRequest.builder()
                    .bucket("bucket-explorer-images")
                    .key(newFilename)
                    .build();
            s3Client.putObject(putObjectRequest, RequestBody.fromBytes(file.getBytes()));
            return "Image updated successfully into S3 bucket";
        }catch (S3Exception e){
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public String deleteFile(String filename) throws IOException {
        if (!doesObjectExist(filename)) {
            throw new ImageDownloadException("Image not exist");
        }
        try{
            DeleteObjectRequest deleteObjectRequest = DeleteObjectRequest.builder()
                    .bucket("bucket-explorer-images")
                    .key(filename)
                    .build();
            s3Client.deleteObject(deleteObjectRequest);
            return "Image deleted successfully";
        }catch (S3Exception e){
            throw new IOException(e.getMessage());
        }
    }

    @Override
    public List<Image> getImagesByProduct(Product product) {
        try {
            // Obtén las imágenes asociadas al producto desde la base de datos

            // Puedes hacer más operaciones aquí si es necesario

            return imageRepository.findByProduct(product);
        } catch (Exception e) {
            // Manejo de excepciones
            e.printStackTrace();
            return Collections.emptyList();
        }
    }



    private boolean doesObjectExist(String objectKey) {
         try {
             HeadObjectRequest headObjectRequest = HeadObjectRequest.builder()
                     .bucket("bucket-explorer-images")
                     .key(objectKey)
                     .build();
             s3Client.headObject(headObjectRequest);
             return true;
         } catch (S3Exception e) {
             if (e.statusCode() == 404) {
                 return false;
             }
         }
         return true;
     }

}
