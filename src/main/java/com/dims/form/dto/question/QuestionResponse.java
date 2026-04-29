package com.dims.form.dto.question;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class QuestionResponse {
    private Long id;
    private Long formId;
    private String name;
    private String choiceType;
    private String choices;
    private Boolean isRequired;
}