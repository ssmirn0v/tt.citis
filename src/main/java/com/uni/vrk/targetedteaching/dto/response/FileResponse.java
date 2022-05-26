package com.uni.vrk.targetedteaching.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class FileResponse {
    private String name;
    private String url;
    private String type;
}
