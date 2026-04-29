package com.dims.form.repository;

import com.dims.form.entity.AllowedDomain;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.UUID;

public interface AllowedDomainRepository extends JpaRepository<AllowedDomain, Long> {

    List<AllowedDomain> findByFormId(Long formId);

    boolean existsByFormIdAndDomain(Long formId, String domain);
}