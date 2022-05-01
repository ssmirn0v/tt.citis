package com.uni.vrk.targetedteaching.dto.request;

import com.uni.vrk.targetedteaching.model.ApplicantStatus;
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
