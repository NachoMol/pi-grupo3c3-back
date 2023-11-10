package com.explorer.equipo3.controller;

import com.explorer.equipo3.service.StorageService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.servlet.Servlet;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("media")
@AllArgsConstructor
public class MediaController {

    private final StorageService storageService;

    private final HttpServletRequest request;

    @PostMapping("upload")
    public List<String> uploadFile(@RequestParam("file") List<MultipartFile> multipartFiles){
        List<String> rutas = new ArrayList<>();
        for (MultipartFile image: multipartFiles) {
            String path = storageService.store(image);
            String host = request.getRequestURL().toString().replace(request.getRequestURI(),"");
            String url = ServletUriComponentsBuilder
                    .fromHttpUrl(host)
                    .path("/media/")
                    .path(path)
                    .toUriString();
            rutas.add(url);
        }


        return rutas;
    }

    @GetMapping("{filename:.+}")
    public ResponseEntity<Resource> getFile(@PathVariable String filename) throws IOException {
        Resource file = storageService.loadAsResource(filename);
        String contenType = Files.probeContentType(file.getFile().toPath());
        return ResponseEntity.ok().header(HttpHeaders.CONTENT_TYPE, contenType).body(file);
    }



}
