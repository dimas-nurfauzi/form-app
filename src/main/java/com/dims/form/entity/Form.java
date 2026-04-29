package com.dims.form.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "forms")
@Getter @Setter
public class Form extends BaseEntity {

    private String name;

    @Column(unique = true)
    private String slug;

    private String description;

    private Boolean limitOneResponse;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "creator_id")
    private User creator;

    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Question> questions = new ArrayList<>();

    @OneToMany(mappedBy = "form", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<AllowedDomain> allowedDomains = new ArrayList<>();
}