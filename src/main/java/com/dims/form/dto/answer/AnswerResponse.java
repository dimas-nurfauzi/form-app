package com.dims.form.dto.answer;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class AnswerResponse {

    private Long questionId;
    private Object value;
}