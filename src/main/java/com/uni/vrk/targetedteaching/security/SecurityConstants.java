package com.uni.vrk.targetedteaching.security;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class SecurityConstants {
    @Value("${tt.security.jwt.secret}")
    private static String secret;

    @Value("${tt.security.jwt.expirationMs}")
    private static Long expiration;
}
