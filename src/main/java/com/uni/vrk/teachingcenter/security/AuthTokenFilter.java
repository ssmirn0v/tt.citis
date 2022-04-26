package com.uni.vrk.teachingcenter.security;

import com.uni.vrk.teachingcenter.security.utility.JWTTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


public class AuthTokenFilter extends OncePerRequestFilter {

    public static final String AUTHORIZATION = "Authorization";
    public static final String BEARER = "Bearer ";

    private UserDetailsService userDetailsService;



    public AuthTokenFilter(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }


//    @Autowired
//    private void setUserDetailsService(UserDetailsServiceImpl userDetailsService) {
//        this.userDetailsService = userDetailsService;
//    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {
        try {
            final String header = request.getHeader(AUTHORIZATION);
            if (header != null || header.startsWith(BEARER)) {
                String token = getTokenFromHeader(header);
                String email = JWTTokenProvider.getSubjectFromToken(token);
                UserDetails userDetails = userDetailsService.loadUserByUsername(email);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails,
                        null,
                        userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
            filterChain.doFilter(request, response);

        } catch (Exception e) {
            logger.error("User authentication error: ", e);
        }
    }

    private String getTokenFromHeader(String header) {
        return header.substring(7);
    }
}
