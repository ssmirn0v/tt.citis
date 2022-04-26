package com.uni.vrk.teachingcenter.dao;

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
public class ApplicantFiles {
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
