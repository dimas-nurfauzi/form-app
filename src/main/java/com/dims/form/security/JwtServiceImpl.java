package com.dims.form.security;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class JwtServiceImpl implements JwtService {

    private final JwtUtil jwtUtil;

    @Override
    public String generateToken(String email) {
        return jwtUtil.generateToken(email);
    }

    @Override
    public String extractEmail(String token) {
        return jwtUtil.extractEmail(token);
    }

    @Override
    public boolean isTokenValid(String token, String email) {
        return jwtUtil.isTokenValid(token, email);
    }
}