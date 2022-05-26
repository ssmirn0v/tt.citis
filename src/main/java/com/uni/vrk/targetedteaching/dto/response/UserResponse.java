package com.uni.vrk.targetedteaching.dto.response;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@SuperBuilder
public class UserResponse {
    private String id;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private String position;
}
