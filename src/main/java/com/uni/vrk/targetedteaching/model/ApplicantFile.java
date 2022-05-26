package com.uni.vrk.targetedteaching.model;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.sql.Blob;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "applicant_files")
public class ApplicantFile {
    @Id
    private String id;
    @NotNull
    private String name;
    @NotNull
    private String type;
    @NotNull
    @Lob
    private byte[] content;

    @NotNull
    private String url;
    @NotNull
    private boolean local;
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Application application;

    public ApplicantFile(String uuid, String fileName, String contentType,
                         byte[] bytes, Application application, String url, boolean local) {
        this.id = uuid;
        this.name = fileName;
        this.type = contentType;
        this.content = bytes;
        this.application = application;
        this.url = url;
        this.local = local;
    }

    public ApplicantFile(String uuid, String fileName, String contentType,
                         Application application, String url, boolean local) {
        this.id = uuid;
        this.name = fileName;
        this.type = contentType;
        this.application = application;
        this.url = url;
        this.local = local;
    }

}
