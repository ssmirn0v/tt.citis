package com.uni.vrk.teachingcenter.security.utility;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Arrays.stream;

@Component
public class JWTTokenProvider {
    public static final String ISSUER = "citis";
    public static final String AUTHORITIES = "Authorities";


    private static String secret;

    private static Long expiration;

    @Value("${tt.security.jwt.secret}")
    public void setSecret(String secret) {
        JWTTokenProvider.secret = secret;
    }

    @Value("${tt.security.jwt.expirationMs}")
    public void setExpiration(Long expiration) {
        JWTTokenProvider.expiration = expiration;
    }

    public static String getJwtToken(UserDetails userDetails) {
        String[] claims = getClaimsFromUser(userDetails);
        return JWT.create()
                .withSubject(userDetails.getUsername())
                .withIssuer(ISSUER)
                .withIssuedAt(new Date())
                .withArrayClaim(AUTHORITIES, claims)
                .withExpiresAt(new Date(System.currentTimeMillis() + expiration))
                .sign(Algorithm.HMAC512(secret.getBytes()));
    }

    public static List<GrantedAuthority> getAuthorities(String token) {
        String[] claims = getClaimsFromToken(token);
        return stream(claims).map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    public static Authentication getAuthentication(String email, List<GrantedAuthority> authorities, HttpServletRequest request) {
        UsernamePasswordAuthenticationToken userPassAuthToken =
                new UsernamePasswordAuthenticationToken(email, null,authorities);
        userPassAuthToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
        return userPassAuthToken;
    }

    public static boolean isTokenValid(String email, String token) {
        final JWTVerifier verifier = getJWTVerifier();
        return StringUtils.isNotEmpty(email) && !isTokenExpired(verifier, token);
    }

    public static String getSubjectFromToken(String token) {
        final JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getSubject();
    }

    private static boolean isTokenExpired(JWTVerifier verifier, String token) {
        Date expiration = verifier.verify(token).getExpiresAt();
        return expiration.before(new Date());
    }

    private static String[] getClaimsFromToken(String token) {
        final JWTVerifier verifier = getJWTVerifier();
        return verifier.verify(token).getClaim(AUTHORITIES).asArray(String.class);
    }

    private static JWTVerifier getJWTVerifier() {
        JWTVerifier verifier;
        try {
            Algorithm algorithm = Algorithm.HMAC512(secret);
            verifier = JWT.require(algorithm).withIssuer(ISSUER).build();
        } catch (JWTVerificationException exception) {
            throw new JWTVerificationException("Incorrect token");
        }
        return verifier;
    }

    private static String[] getClaimsFromUser(UserDetails userDetails) {
        List<String> authorities = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : userDetails.getAuthorities()) {
            authorities.add(grantedAuthority.getAuthority());
        }
        return authorities.toArray(String[]::new);
    }
}
