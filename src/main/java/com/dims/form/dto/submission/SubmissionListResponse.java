package com.dims.form.dto.submission;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;
import java.util.Map;

@Getter
@AllArgsConstructor
public class SubmissionListResponse {
    private String message;
    private List<SubmissionItem> responses;

    @Getter
    @AllArgsConstructor
    public static class SubmissionItem {
        private String date;
        private UserInfo user;
        private Map<String, String> answers;

        @Getter
        @AllArgsConstructor
        public static class UserInfo {
            private Long id;
            private String name;
            private String email;
        }
    }
}