package com.dims.form.entity;

import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "allowed_domains")
@Getter @Setter
public class AllowedDomain extends BaseEntity {

    private String domain;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "form_id")
    private Form form;
}