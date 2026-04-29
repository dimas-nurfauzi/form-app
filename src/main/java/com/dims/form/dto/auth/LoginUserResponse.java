package com.dims.form.dto.auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

@Getter
@AllArgsConstructor
@Builder
public class LoginUserResponse {
    private String name;
    private String email;
    private String accessToken;
}