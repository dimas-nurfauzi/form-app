package com.dims.form.repository;

import com.dims.form.entity.Submission;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface SubmissionRepository extends JpaRepository<Submission, Long> {
    boolean existsByFormIdAndUserId(Long formId, Long userId);
    List<Submission> findByFormId(Long formId);
}