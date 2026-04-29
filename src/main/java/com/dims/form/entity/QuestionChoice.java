package com.dims.form.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "question_choices")
@Getter @Setter
public class QuestionChoice extends BaseEntity {

    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_id")
    private Question question;
}