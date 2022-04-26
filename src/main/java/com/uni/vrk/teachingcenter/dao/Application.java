package com.uni.vrk.teachingcenter.dao;

import com.sun.istack.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.apache.tomcat.jni.Local;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;
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
    @NotNull
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
    @NotNull
    private boolean consentPersonalData;

    private String university;

    private String direction;

    @NotNull
    private ApplicantStatus status;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<ApplicantFiles> files;

    @NotNull
    private String file1;
    @NotNull
    private String file2;
    @NotNull
    private String file3;
    private String file4;
    private String file5;


}
