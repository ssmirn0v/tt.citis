package com.uni.vrk.teachingcenter.interfaces;

import com.uni.vrk.teachingcenter.dto.request.AuthRequest;
import com.uni.vrk.teachingcenter.dto.request.RegistrationRequest;
import com.uni.vrk.teachingcenter.dto.response.AuthResponse;
import com.uni.vrk.teachingcenter.dto.response.MessageResponse;

public interface AuthService {
    AuthResponse authenticate(AuthRequest authRequest);
    MessageResponse register(RegistrationRequest registrationRequest);
}
