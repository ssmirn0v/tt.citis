package com.uni.vrk.targetedteaching.controllers;


import com.uni.vrk.targetedteaching.dto.request.AssignRequest;
import com.uni.vrk.targetedteaching.dto.response.CustomResponse;
import com.uni.vrk.targetedteaching.interfaces.StatusService;
import com.uni.vrk.targetedteaching.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.Map;

@Slf4j
@CrossOrigin(origins = "*")
@Controller
@RequestMapping("/application/{application_id}/status")
public class StatusController {

    StatusService statusService;
    UserRepository userRepository;

    @CrossOrigin(origins = "*")
    @PreAuthorize("hasRole('ROLE_ANALYST')")
    @PostMapping("/assign")
    public ResponseEntity<CustomResponse> assignApplication(@PathVariable String applicationId, @RequestBody AssignRequest assignRequest) {
        statusService.assignApplication(applicationId, assignRequest);
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(null)
                        .message("Заявление прикреплено к вам")
                        .status(HttpStatus.NO_CONTENT)
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .build()
        );
    }

    @CrossOrigin(origins = "*")
    @PreAuthorize("hasRole('ROLE_ANALYST')")
    @GetMapping("/select")
    public ResponseEntity<CustomResponse> selectApplication(@PathVariable String applicationId) {
        statusService.selectApplication(applicationId);
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(null)
                        .message("Кандидат отобран")
                        .status(HttpStatus.NO_CONTENT)
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .build()
        );
    }

    @CrossOrigin(origins = "*")
    @PreAuthorize("hasRole('ROLE_ANALYST')")
    @GetMapping("/drop")
    public ResponseEntity<CustomResponse> dropApplication(@PathVariable String applicationId) {
        statusService.dropApplication(applicationId);
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(null)
                        .message("Кандидат выбыл")
                        .status(HttpStatus.NO_CONTENT)
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .build()
        );
    }

}
