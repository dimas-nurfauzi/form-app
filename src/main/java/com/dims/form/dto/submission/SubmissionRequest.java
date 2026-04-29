package com.dims.form.dto.submission;

import com.dims.form.dto.answer.AnswerRequest;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter @Setter
public class SubmissionRequest {

    @NotEmpty
    private List<AnswerRequest> answers;
}