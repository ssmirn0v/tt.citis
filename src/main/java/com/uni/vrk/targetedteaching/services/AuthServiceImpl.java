package com.uni.vrk.targetedteaching.services;

import com.uni.vrk.targetedteaching.model.Role;
import com.uni.vrk.targetedteaching.model.RoleE;
import com.uni.vrk.targetedteaching.model.UserC;
import com.uni.vrk.targetedteaching.dto.request.AuthRequest;
import com.uni.vrk.targetedteaching.dto.request.RegistrationRequest;
import com.uni.vrk.targetedteaching.dto.response.AuthResponse;
import com.uni.vrk.targetedteaching.dto.response.MessageResponse;
import com.uni.vrk.targetedteaching.interfaces.AuthService;
import com.uni.vrk.targetedteaching.repository.RoleRepository;
import com.uni.vrk.targetedteaching.repository.UserRepository;
import com.uni.vrk.targetedteaching.security.UserDetailsImpl;
import com.uni.vrk.targetedteaching.security.utility.JWTTokenProvider;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Slf4j
@Service
public class AuthServiceImpl implements AuthService {


    UserRepository userRepository;
    RoleRepository roleRepository;

    AuthenticationManager authenticationManager;

    PasswordEncoder passwordEncoder;

    @Autowired
    private void setRoleRepository(RoleRepository roleRepository) {
        this.roleRepository = roleRepository;
    }

    @Autowired
    private void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    @Autowired
    private void setAuthenticationManager(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }


    @Autowired
    private void setUserRepository(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public AuthResponse authenticate(AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getEmail(), authRequest.getPassword())
        );


        SecurityContextHolder.getContext().setAuthentication(authentication);
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String jwt = JWTTokenProvider.getJwtToken(userDetails);

        List<String> roles = userDetails.getAuthorities().stream()
                .map(authority -> authority.toString())
                .collect(Collectors.toList());

        return new AuthResponse(jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                roles);
    }

    @Override
    public MessageResponse register(RegistrationRequest registrationRequest) {


        if (userRepository.existsByEmail(registrationRequest.getEmail())) {
            return new MessageResponse("Error: Email is already in use!");
        }

        UserC user = new UserC(registrationRequest.getFirstName(),
                registrationRequest.getLastName(),
                registrationRequest.getPatronymic(),
                registrationRequest.getEmail(),
                passwordEncoder.encode(registrationRequest.getPassword())
                );

        Set<Role> roles = new HashSet<>();
        Set<String> requestRoles = registrationRequest.getRole();
        if (requestRoles == null | requestRoles.size() == 0) {
            Role analystRole = roleRepository.findByName(RoleE.ROLE_ANALYST)
                    .orElseThrow(() -> new EntityNotFoundException("Error: role is not found"));
            roles.add(analystRole);

        } else {
            for(String role : requestRoles) {
                switch (role) {
                    case "analyst":
                        Role analystRole = roleRepository.findByName(RoleE.ROLE_ANALYST)
                                .orElseThrow(() -> new EntityNotFoundException("Error: role is not found"));
                        roles.add(analystRole);

                        break;
                    case "admin":
                        Role adminRole = roleRepository.findByName(RoleE.ROLE_ADMIN)
                                .orElseThrow(() -> new EntityNotFoundException("Error: role is not found"));
                        roles.add(adminRole);

                        break;
                    default:
                        throw new RuntimeException("Error: role does not exist");
                }
            }

        }

        user.setRoles(roles);
        userRepository.save(user);
        return new MessageResponse("User registered successfully");
    }
}
