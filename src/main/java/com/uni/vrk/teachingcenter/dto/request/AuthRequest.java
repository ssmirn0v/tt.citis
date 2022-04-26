package com.uni.vrk.teachingcenter.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthRequest {

    private String email;
    private String password;
}
