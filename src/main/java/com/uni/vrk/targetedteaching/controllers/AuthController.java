package com.uni.vrk.targetedteaching.controllers;


import com.uni.vrk.targetedteaching.dto.request.AuthRequest;
import com.uni.vrk.targetedteaching.dto.request.RegistrationRequest;
import com.uni.vrk.targetedteaching.interfaces.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
public class AuthController {

    AuthService authService;

    @Autowired
    private void setAuthService(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/login")
    public ResponseEntity<?> authenticate(@RequestBody AuthRequest authRequest) {
        return ResponseEntity.ok(authService.authenticate(authRequest));
    }

//    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegistrationRequest registrationRequest) {

        return ResponseEntity.ok(authService.register(registrationRequest));
    }
}
