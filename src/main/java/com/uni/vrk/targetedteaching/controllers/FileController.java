package com.uni.vrk.targetedteaching.controllers;

import com.uni.vrk.targetedteaching.dto.response.CustomResponse;
import com.uni.vrk.targetedteaching.dto.response.FileResponse;
import com.uni.vrk.targetedteaching.interfaces.FileStorageService;
import com.uni.vrk.targetedteaching.model.ApplicantFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.Map;

@Controller
@CrossOrigin(origins = "*")
public class FileController {

    @Autowired
    private FileStorageService fileStorageService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/application/{applicationId}/upload")
    public ResponseEntity<CustomResponse> uploadFile(
            @PathVariable String applicationId,
            @RequestParam("file") MultipartFile file) throws IOException {
        fileStorageService.store(file, applicationId);
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("applicantFile", fileStorageService.store(file, applicationId)))
                        .message("Файл успешно загружен")
                        .status(HttpStatus.NO_CONTENT)
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .build()
        );
    }

    @GetMapping("/file/{id}")
    public ResponseEntity<byte[]> getFile(@PathVariable String id) {
        ApplicantFile file = fileStorageService.getFile(id);
        MediaType mediaType = MediaType.parseMediaType(file.getType());
        return ResponseEntity.ok()
                .contentType(mediaType)
                .body(file.getContent());
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/application/{applicationId}/file/{id}")
    public ResponseEntity<CustomResponse> deleteFile(
            @PathVariable String applicationId,
            @PathVariable String id) {
        fileStorageService.deleteFile(applicationId, id);
        return ResponseEntity.ok(
                CustomResponse.builder()
                .timeStamp(LocalDateTime.now())
                .message("Файл успешно удален")
                .status(HttpStatus.NO_CONTENT)
                .statusCode(HttpStatus.NO_CONTENT.value())
                .build()
        );
    }


}
