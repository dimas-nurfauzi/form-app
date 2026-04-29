package com.dims.form.repository;

import com.dims.form.entity.Form;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface FormRepository extends JpaRepository<Form, Long> {
    boolean existsBySlug(String slug);
    List<Form> findByCreatorId(Long creatorId);

    // untuk getDetail — fetch allowedDomains + questions + choices
    @Query("""
        SELECT DISTINCT f FROM Form f
        LEFT JOIN FETCH f.allowedDomains
        WHERE f.slug = :slug
    """)
    Optional<Form> findBySlugWithDomains(@Param("slug") String slug);

    // untuk submit & getSubmissions — fetch allowedDomains + creator
    @Query("""
        SELECT f FROM Form f
        LEFT JOIN FETCH f.allowedDomains
        JOIN FETCH f.creator
        WHERE f.slug = :slug
    """)
    Optional<Form> findBySlugWithDomainsAndCreator(@Param("slug") String slug);
}