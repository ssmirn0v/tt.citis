package com.uni.vrk.teachingcenter.dto.request;

import com.fasterxml.jackson.annotation.JacksonAnnotation;
import com.uni.vrk.teachingcenter.dao.ApplicantStatus;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateApplicationRequest {
    private String firstName;
    private String lastName;
    private String patronymic;
    private String phoneNumber;
    private String email;
    private LocalDate dateOfBirth;
    private String snils;
    private String university;
    private String direction;
    private boolean consentPersonalData;
    private ApplicantStatus status;
}
