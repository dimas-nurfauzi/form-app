package com.dims.form.service;

import com.dims.form.dto.question.QuestionAddResponse;
import com.dims.form.dto.question.QuestionRequest;
import com.dims.form.entity.User;

import java.util.UUID;

public interface QuestionService {
    QuestionAddResponse addQuestion(String slug, QuestionRequest request, User user);
    void deleteQuestion(String slug, Long questionId, User user);
}