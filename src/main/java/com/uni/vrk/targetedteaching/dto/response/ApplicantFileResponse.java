package com.uni.vrk.targetedteaching.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@AllArgsConstructor
@SuperBuilder
public class ApplicantFileResponse {
    private String id;
    private String name;
    private String url;
    private String type;

}
