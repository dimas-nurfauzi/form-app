package com.dims.form.dto.form;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class FormListResponse {
    private String message;
    private List<FormItem> forms;

    @Getter
    @AllArgsConstructor
    public static class FormItem {
        private Long id;
        private String name;
        private String slug;
        private String description;
        private Boolean limitOneResponse;
        private Long creatorId;
    }
}