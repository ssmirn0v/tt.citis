package com.uni.vrk.targetedteaching.dto.response;

import com.sun.istack.NotNull;
import com.uni.vrk.targetedteaching.model.ApplicantFile;
import com.uni.vrk.targetedteaching.model.ApplicantStatus;
import com.uni.vrk.targetedteaching.model.UserC;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@SuperBuilder
public class ApplicationResponse {
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
    private List<ApplicantFileResponse> files;
    private UserResponse supervisor;
}
