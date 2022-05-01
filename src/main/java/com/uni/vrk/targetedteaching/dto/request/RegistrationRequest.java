package com.uni.vrk.targetedteaching.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.Set;

@Data
@AllArgsConstructor
public class RegistrationRequest {

    private String email;

    private String firstName;

    private String lastName;

    private String patronymic;

    private Set<String> role;

    private String password;

}
