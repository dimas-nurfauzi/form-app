package com.dims.form.dto.question;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class QuestionRequest {

    @NotBlank(message = "Question name is required")
    private String name;

    @NotBlank(message = "Choice type is required")
    private String choiceType;

    private List<
            @NotBlank(message = "Choice value must not be blank")
                    String
            > choices;

    @NotNull(message = "isRequired must not be null")
    private Boolean isRequired;
}