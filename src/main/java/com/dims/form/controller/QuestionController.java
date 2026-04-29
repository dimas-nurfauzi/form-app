package com.dims.form.controller;

import com.dims.form.dto.question.QuestionAddResponse;
import com.dims.form.dto.question.QuestionRequest;
import com.dims.form.entity.User;
import com.dims.form.security.UserDetailsImpl;
import com.dims.form.service.QuestionService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/forms/{slug}/questions")
@RequiredArgsConstructor
public class QuestionController {

    private final QuestionService questionService;

    @PostMapping
    public ResponseEntity<QuestionAddResponse> addQuestion(
            @PathVariable String slug,
            @Valid @RequestBody QuestionRequest request,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User user = userDetails.getUser();
        return ResponseEntity.ok(questionService.addQuestion(slug, request, user));
    }

    @DeleteMapping("/{questionId}")
    public ResponseEntity<Map<String, String>> deleteQuestion(
            @PathVariable String slug,
            @PathVariable Long questionId,
            @AuthenticationPrincipal UserDetailsImpl userDetails
    ) {
        User user = userDetails.getUser();
        questionService.deleteQuestion(slug, questionId, user);
        return ResponseEntity.ok(Map.of("message", "Remove question success"));
    }
}