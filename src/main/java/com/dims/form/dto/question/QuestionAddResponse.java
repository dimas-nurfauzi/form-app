package com.dims.form.dto.question;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionAddResponse {
    private String message;
    private QuestionBody question;

    @Getter
    @AllArgsConstructor
    public static class QuestionBody {
        private Long id;
        private String name;
        private String choiceType;
        private Boolean isRequired;
        private String choices;
        private Long formId;
    }
}