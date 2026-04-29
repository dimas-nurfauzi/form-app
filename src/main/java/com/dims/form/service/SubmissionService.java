package com.dims.form.service;

import com.dims.form.dto.submission.SubmissionListResponse;
import com.dims.form.dto.submission.SubmissionRequest;
import com.dims.form.entity.User;

public interface SubmissionService {
    void submit(String slug, SubmissionRequest request, User user);
    SubmissionListResponse getSubmissions(String slug, User user);
}