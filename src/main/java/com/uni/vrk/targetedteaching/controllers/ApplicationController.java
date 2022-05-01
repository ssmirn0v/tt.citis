package com.uni.vrk.targetedteaching.controllers;


import com.uni.vrk.targetedteaching.dto.request.NewApplicationRequest;
import com.uni.vrk.targetedteaching.dto.request.UpdateApplicationRequest;
import com.uni.vrk.targetedteaching.dto.response.CustomResponse;
import com.uni.vrk.targetedteaching.interfaces.ApplicationService;
import com.uni.vrk.targetedteaching.interfaces.CSVService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/application")
public class ApplicationController {

    private ApplicationService applicationService;
    private CSVService csvService;

    @Autowired
    private void setCsvService(CSVService csvService) {
        this.csvService = csvService;
    }

    @Autowired
    private void setApplicationService(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @CrossOrigin(origins = "*")
    @PreAuthorize("hasAnyRole('ROLE_ADMIN', 'ROLE_ANALYST')")
    @GetMapping("/all")
    public ResponseEntity<CustomResponse> getAllApplications() {
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("applications", applicationService.getAllApplications()))
                        .message("Applications retrieved")
                        .status(HttpStatus.OK)
                        .statusCode(HttpStatus.OK.value())
                        .build()
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/save")
    public ResponseEntity<CustomResponse> saveApplication(@RequestBody NewApplicationRequest applicationRequest) {
        return ResponseEntity.ok(
                CustomResponse.builder()
                .timeStamp(LocalDateTime.now())
                .data(Map.of("application", applicationService.saveApplication(applicationRequest)))
                .message("Application saved")
                .status(HttpStatus.CREATED)
                .statusCode(HttpStatus.CREATED.value())
                .build()
        );
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}/update")
    public ResponseEntity<CustomResponse> updateApplication(
            @PathVariable String id,
            @RequestBody UpdateApplicationRequest updateApplicationRequest) {
        applicationService.updateApplication(id, updateApplicationRequest);
        return ResponseEntity.ok(
                CustomResponse.builder()
                .timeStamp(LocalDateTime.now())
                .data(null)
                .message("Application updated")
                .status(HttpStatus.NO_CONTENT)
                .statusCode(HttpStatus.NO_CONTENT.value())
                .build()
        );
    }

    @CrossOrigin(origins = "http://localhost:4200")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @DeleteMapping("/{id}/delete")
    public ResponseEntity<CustomResponse> deleteApplication(@PathVariable String id) {
        applicationService.deleteApplication(id);
        return ResponseEntity.ok(
          CustomResponse.builder()
          .timeStamp(LocalDateTime.now())
          .data(null)
          .message("Application deleted")
          .status(HttpStatus.NO_CONTENT)
          .statusCode(HttpStatus.NO_CONTENT.value())
          .build()
        );
    }

    @CrossOrigin(origins = "*")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/upload")
    public ResponseEntity<CustomResponse> uploadApplications(@RequestParam("file") MultipartFile file) {
        log.info("CSV UPLOAD MAPPING");
        try {
            csvService.saveApplications(file);
            log.info("saving csv");
            return ResponseEntity.ok(
                    CustomResponse.builder()
                            .timeStamp(LocalDateTime.now())
                            .message("CSV file uploaded")
                            .status(HttpStatus.OK)
                            .statusCode(HttpStatus.OK.value())
                            .build()
            );
        } catch (Exception e) {
            log.error("exception csv: " + e.getMessage());
            return ResponseEntity.ok(
                    CustomResponse.builder()
                            .timeStamp(LocalDateTime.now())
                            .message("Fail to upload csv file")
                            .status(HttpStatus.BAD_REQUEST)
                            .statusCode(HttpStatus.BAD_REQUEST.value())
                            .build()
            );
        }
    }
}
