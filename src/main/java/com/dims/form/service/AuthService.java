// service/AuthService.java
package com.dims.form.service;

import com.dims.form.dto.auth.LoginRequest;
import com.dims.form.dto.auth.LoginUserResponse;

public interface AuthService {
    LoginUserResponse login(LoginRequest request);
    void logout(String token);
}