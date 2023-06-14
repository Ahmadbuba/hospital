package com.system.hospital.controller;

import com.google.cloud.storage.*;
import com.system.hospital.model.Photo;
import com.system.hospital.repository.PhotoRepository;
import com.system.hospital.service.FileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/photos")
public class PhotoController {
    @Autowired
    FileService fileService;

    @PostMapping
    public ResponseEntity<String> uploadPhoto(@RequestParam MultipartFile file) throws IOException {
        fileService.uploadFile(file);
        return ResponseEntity.ok("File uploaded successfully");
    }
}
