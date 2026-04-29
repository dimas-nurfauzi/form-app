package com.dims.form.service.impl;

import com.dims.form.dto.form.*;
import com.dims.form.dto.question.QuestionResponse;
import com.dims.form.entity.*;
import com.dims.form.exception.DuplicateResourceException;
import com.dims.form.exception.ForbiddenException;
import com.dims.form.exception.ResourceNotFoundException;
import com.dims.form.repository.*;
import com.dims.form.service.FormService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional
public class FormServiceImpl implements FormService {

    private final FormRepository formRepository;
    private final AllowedDomainRepository domainRepository;

    @Override
    public FormCreateResponse create(FormRequest request, User user) {
        if (formRepository.existsBySlug(request.getSlug())) {
            throw new DuplicateResourceException("The slug has already been taken.");
        }

        Form form = new Form();
        form.setName(request.getName());
        form.setSlug(request.getSlug());
        form.setDescription(request.getDescription());
        form.setLimitOneResponse(request.getLimitOneResponse());
        form.setCreator(user);
        formRepository.save(form);

        if (request.getAllowedDomains() != null) {
            for (String d : request.getAllowedDomains()) {
                AllowedDomain domain = new AllowedDomain();
                domain.setForm(form);
                domain.setDomain(d);
                domainRepository.save(domain);
            }
        }

        return new FormCreateResponse(
                "Create form success",
                new FormCreateResponse.FormBody(
                        form.getId(),
                        form.getName(),
                        form.getSlug(),
                        form.getDescription(),
                        form.getLimitOneResponse(),
                        user.getId()
                )
        );
    }

    @Override
    public FormListResponse getMyForms(User user) {
        List<FormListResponse.FormItem> items = formRepository
                .findByCreatorId(user.getId())
                .stream()
                .map(f -> new FormListResponse.FormItem(
                        f.getId(),
                        f.getName(),
                        f.getSlug(),
                        f.getDescription(),
                        f.getLimitOneResponse(),
                        f.getCreator().getId()
                ))
                .toList();

        return new FormListResponse("Get all forms success", items);
    }

    @Override
    @Transactional(readOnly = true)
    public FormDetailResponse getDetail(String slug, User currentUser) {
        Form form = formRepository.findBySlugWithDomains(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Form not found"));

        List<String> domains = form.getAllowedDomains()
                .stream()
                .map(AllowedDomain::getDomain)
                .toList();

        if (!domains.isEmpty() && currentUser != null) {
            String userDomain = currentUser.getEmail()
                    .substring(currentUser.getEmail().indexOf("@") + 1);
            boolean allowed = domains.stream()
                    .anyMatch(d -> d.equalsIgnoreCase(userDomain));
            if (!allowed) {
                throw new ForbiddenException("Forbidden access");
            }
        }

        List<QuestionResponse> questions = form.getQuestions()
                .stream()
                .map(q -> {
                    String choices = q.getChoices().isEmpty() ? null
                            : String.join(",", q.getChoices().stream()
                            .map(QuestionChoice::getValue)
                            .toList());
                    return new QuestionResponse(
                            q.getId(),
                            form.getId(),
                            q.getName(),
                            q.getChoiceType().name().toLowerCase().replace("_", " "),
                            choices,
                            q.getIsRequired()
                    );
                })
                .toList();

        return new FormDetailResponse(
                "Get form success",
                new FormDetailResponse.FormDetail(
                        form.getId(),
                        form.getName(),
                        form.getSlug(),
                        form.getDescription(),
                        form.getLimitOneResponse(),
                        form.getCreator().getId(),
                        domains,
                        questions
                )
        );
    }
}