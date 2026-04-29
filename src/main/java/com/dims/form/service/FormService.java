package com.dims.form.service;

import com.dims.form.dto.form.FormCreateResponse;
import com.dims.form.dto.form.FormDetailResponse;
import com.dims.form.dto.form.FormListResponse;
import com.dims.form.dto.form.FormRequest;
import com.dims.form.entity.User;

public interface FormService {
    FormCreateResponse create(FormRequest request, User user);
    FormListResponse getMyForms(User user);
    FormDetailResponse getDetail(String slug, User currentUser);
}