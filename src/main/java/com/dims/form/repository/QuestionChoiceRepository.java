package com.dims.form.repository;

import com.dims.form.entity.QuestionChoice;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface QuestionChoiceRepository extends JpaRepository<QuestionChoice, Long> {

}