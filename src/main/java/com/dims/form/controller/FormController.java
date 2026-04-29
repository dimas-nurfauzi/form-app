package com.dims.form.controller;

import com.dims.form.dto.form.*;
import com.dims.form.entity.User;
import com.dims.form.security.UserDetailsImpl;
import com.dims.form.service.FormService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/forms")
@RequiredArgsConstructor
public class FormController {

    private final FormService formService;

    @PostMapping
    public ResponseEntity<FormCreateResponse> create(
            @Valid @RequestBody FormRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User user = userDetails.getUser();
        return ResponseEntity.ok(formService.create(request, user));
    }

    @GetMapping
    public ResponseEntity<FormListResponse> getMyForms(
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User user = userDetails.getUser();
        return ResponseEntity.ok(formService.getMyForms(user));
    }

    @GetMapping("/{slug}")
    public ResponseEntity<FormDetailResponse> getDetail(
            @PathVariable String slug
    ) {

        Authentication auth = SecurityContextHolder.getContext().getAuthentication();

        User user = null;

        if (auth != null && auth.getPrincipal() instanceof UserDetailsImpl u) {
            user = u.getUser();
        }

        return ResponseEntity.ok(formService.getDetail(slug, user));
    }
}