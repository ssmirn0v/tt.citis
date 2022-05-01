package com.uni.vrk.targetedteaching.interfaces;

import com.uni.vrk.targetedteaching.dto.request.AuthRequest;
import com.uni.vrk.targetedteaching.dto.request.RegistrationRequest;
import com.uni.vrk.targetedteaching.dto.response.AuthResponse;
import com.uni.vrk.targetedteaching.dto.response.MessageResponse;

public interface AuthService {
    AuthResponse authenticate(AuthRequest authRequest);
    MessageResponse register(RegistrationRequest registrationRequest);
}
