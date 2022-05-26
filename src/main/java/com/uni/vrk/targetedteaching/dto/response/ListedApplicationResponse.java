package com.uni.vrk.targetedteaching.dto.response;

import com.uni.vrk.targetedteaching.model.ApplicantStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@NoArgsConstructor
@SuperBuilder
public class ListedApplicationResponse {
    private String applicationId;
    private String firstName;
    private String lastName;
    private String patronymic;
    private String email;
    private LocalDateTime applicationTime;
    private LocalDate dateOfBirth;
    private String snils;
    private String phoneNumber;
    private String university;
    private String direction;
    private ApplicantStatus status;
}
