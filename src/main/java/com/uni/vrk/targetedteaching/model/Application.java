package com.uni.vrk.targetedteaching.model;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

@SuperBuilder
@NoArgsConstructor
@Entity
@Data
@Table(name = "applications")
public class Application {
    @Id
    @GeneratedValue
    private Long id;
    @Column(unique = true)
    @NotNull
    private String applicationId;
    @NotNull
    private String firstName;
    @NotNull
    private String lastName;
    private String patronymic;
    @NotNull
    private String email;
    @NotNull
    private LocalDateTime applicationTime;

    private LocalDate dateOfBirth;
    @NotNull
    private String snils;
    @NotNull
    private String phoneNumber;

    private String university;

    private String direction;

    @NotNull
    private ApplicantStatus status;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private List<ApplicantFile> files;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.DETACH)
    private UserC supervisor;


}
