package com.dims.form.controller;

import com.dims.form.dto.submission.SubmissionListResponse;
import com.dims.form.dto.submission.SubmissionRequest;
import com.dims.form.entity.User;
import com.dims.form.security.UserDetailsImpl;
import com.dims.form.service.SubmissionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/forms/{slug}/responses")
@RequiredArgsConstructor
public class SubmissionController {

    private final SubmissionService submissionService;

    @PostMapping
    public ResponseEntity<Map<String, String>> submit(
            @PathVariable String slug,
            @Valid @RequestBody SubmissionRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User user = userDetails.getUser();
        submissionService.submit(slug, request, user);
        return ResponseEntity.ok(Map.of("message", "Submit response success"));
    }

    @GetMapping
    public ResponseEntity<SubmissionListResponse> getSubmissions(
            @PathVariable String slug,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User user = userDetails.getUser();
        return ResponseEntity.ok(submissionService.getSubmissions(slug, user));
    }
}