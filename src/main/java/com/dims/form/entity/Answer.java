package com.dims.form.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "answers")
@Getter
@Setter
public class Answer extends BaseEntity {

    private String value;

    @ManyToOne
    @JoinColumn(name = "submission_id", nullable = false)
    private Submission submission;
    
    @ManyToOne
    @JoinColumn(name = "question_id")
    private Question question;
}