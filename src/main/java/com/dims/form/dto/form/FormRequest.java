package com.dims.form.dto.form;

import java.util.List;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public class FormRequest {

    @NotBlank(message = "Name is required")
    private String name;

    @NotBlank(message = "Slug is required")
    @Pattern(regexp = "^[a-z0-9-]+$", message = "Slug must be lowercase and use hyphen only")
    private String slug;

    private String description;

    @NotNull(message = "limitOneResponse must not be null")
    private Boolean limitOneResponse;

    private List<
            @Pattern(
                    regexp = "^[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$",
                    message = "Invalid domain format"
            )
                    String
            > allowedDomains;
}