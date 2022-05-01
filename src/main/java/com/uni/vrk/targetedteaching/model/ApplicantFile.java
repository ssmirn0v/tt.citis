package com.uni.vrk.targetedteaching.model;

import com.sun.istack.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "applicant_files")
public class ApplicantFile {
    @Id
    @GeneratedValue
    private Long id;
    @NotNull
    private String name;
    @NotNull
    private FileType type;
    @NotNull
    @Lob
    private Blob content;
    @ManyToOne(fetch = FetchType.LAZY)
    private Application application;
}
