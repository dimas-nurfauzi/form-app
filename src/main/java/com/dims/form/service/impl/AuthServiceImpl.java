package com.dims.form.service.impl;

import com.dims.form.dto.auth.LoginRequest;
import com.dims.form.dto.auth.LoginUserResponse;
import com.dims.form.entity.User;
import com.dims.form.repository.UserRepository;
import com.dims.form.security.JwtUtil;
import com.dims.form.security.TokenBlacklist;
import com.dims.form.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.AuthenticationException;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;
    private final TokenBlacklist tokenBlacklist;

    @Override
    public LoginUserResponse login(LoginRequest request) {
        try {
            authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getEmail(),
                            request.getPassword()
                    )
            );
        } catch (AuthenticationException e) {
            log.warn("Failed login for: {}", request.getEmail());
            throw new BadCredentialsException("Email or password incorrect");
        }

        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new BadCredentialsException("Email or password incorrect"));

        String token = jwtUtil.generateToken(user.getEmail());

        return LoginUserResponse.builder()
                .name(user.getName())
                .email(user.getEmail())
                .accessToken(token)
                .build();
    }

    @Override
    public void logout(String token) {
        tokenBlacklist.blacklist(token);
    }
}