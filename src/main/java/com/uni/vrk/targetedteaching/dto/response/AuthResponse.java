package com.uni.vrk.targetedteaching.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class AuthResponse {
    private String jwt;
    private String id;
    private String email;
    private List<String> roles;
}
