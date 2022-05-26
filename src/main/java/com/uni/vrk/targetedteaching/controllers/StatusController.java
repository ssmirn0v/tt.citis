package com.uni.vrk.targetedteaching.controllers;


import com.uni.vrk.targetedteaching.dto.request.AssignRequest;
import com.uni.vrk.targetedteaching.dto.response.CustomResponse;
import com.uni.vrk.targetedteaching.interfaces.StatusService;
import com.uni.vrk.targetedteaching.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
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
@RequestMapping("/application/{applicationId}/status")
public class StatusController {


    @Autowired
    StatusService statusService;
    UserRepository userRepository;

    @CrossOrigin(origins = "*")
    @PreAuthorize("hasRole('ROLE_ANALYST')")
    @GetMapping("/assign")
    public ResponseEntity<CustomResponse> assignApplication(@PathVariable String applicationId) {
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(Map.of("supervisor",statusService.assignApplication(applicationId)))
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

    @PreAuthorize("hasRole('ROLE_ANALYST')")
    @GetMapping("/free")
    public ResponseEntity<CustomResponse> freeApplication(@PathVariable String applicationId) {
        statusService.freeApplication(applicationId);
        return ResponseEntity.ok(
                CustomResponse.builder()
                        .timeStamp(LocalDateTime.now())
                        .data(null)
                        .message("Заявление теперь свободно")
                        .status(HttpStatus.NO_CONTENT)
                        .statusCode(HttpStatus.NO_CONTENT.value())
                        .build()
        );
    }

}
