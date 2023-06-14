package com.system.hospital.controller;

import com.google.cloud.storage.*;
import com.system.hospital.model.Photo;
import com.system.hospital.repository.PhotoRepository;
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
    private final Storage storage;
    private final PhotoRepository photoRepository;

    @Autowired
    public PhotoController(PhotoRepository photoRepository) {
        this.photoRepository = photoRepository;
        storage = StorageOptions.getDefaultInstance().getService();
    }

    @PostMapping
    public ResponseEntity<Photo> uploadPhoto(@RequestParam("file") MultipartFile file) {
        try {
            byte[] photoBytes = file.getBytes();
            String photoName = file.getOriginalFilename();
            String bucketName = "your-bucket-name";

            BlobId blobId = BlobId.of(bucketName, photoName);
            Blob blob = storage.create(
                    BlobInfo.newBuilder(blobId).setContentType(file.getContentType()).build(),
                    photoBytes
            );

            String photoUrl = blob.getMediaLink();
            Photo photo = Photo.builder()
                    .photoName(photoName)
                    .photoUrl(photoUrl)
                    .build();
            photoRepository.save(photo);

            return ResponseEntity.ok(photo);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
