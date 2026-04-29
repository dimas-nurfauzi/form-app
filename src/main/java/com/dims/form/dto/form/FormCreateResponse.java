package com.dims.form.dto.form;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class FormCreateResponse {
    private String message;
    private FormBody form;

    @Getter
    @AllArgsConstructor
    public static class FormBody {
        private Long id;
        private String name;
        private String slug;
        private String description;
        private Boolean limitOneResponse;
        private Long creatorId;
    }
}