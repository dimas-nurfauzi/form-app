package com.dims.form.dto.form;

import com.dims.form.dto.question.QuestionResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FormDetailResponse {
    private String message;
    private FormDetail form;

    @Getter
    @AllArgsConstructor
    public static class FormDetail {
        private Long id;
        private String name;
        private String slug;
        private String description;
        private Boolean limitOneResponse;
        private Long creatorId;
        private List<String> allowedDomains;
        private List<QuestionResponse> questions;
    }
}