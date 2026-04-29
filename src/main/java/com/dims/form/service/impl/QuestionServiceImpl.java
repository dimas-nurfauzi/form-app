package com.dims.form.service.impl;

import com.dims.form.dto.question.QuestionAddResponse;
import com.dims.form.dto.question.QuestionRequest;
import com.dims.form.entity.*;
import com.dims.form.exception.ForbiddenException;
import com.dims.form.exception.InvalidFieldException;
import com.dims.form.exception.ResourceNotFoundException;
import com.dims.form.repository.*;
import com.dims.form.service.QuestionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class QuestionServiceImpl implements QuestionService {

    private final FormRepository formRepository;
    private final QuestionRepository questionRepository;
    private final QuestionChoiceRepository choiceRepository;

    @Override
    public QuestionAddResponse addQuestion(String slug, QuestionRequest request, User user) {
        Form form = formRepository.findBySlugWithDomainsAndCreator(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Form not found"));

        if (!form.getCreator().getId().equals(user.getId())) {
            throw new ForbiddenException("Forbidden access");
        }

        ChoiceType choiceType;
        try {
            choiceType = ChoiceType.valueOf(
                    request.getChoiceType().toUpperCase().replace(" ", "_")
            );
        } catch (IllegalArgumentException e) {
            throw new InvalidFieldException("Invalid choice type: " + request.getChoiceType());
        }

        boolean requiresChoices = choiceType == ChoiceType.MULTIPLE_CHOICE
                || choiceType == ChoiceType.DROPDOWN
                || choiceType == ChoiceType.CHECKBOX;

        if (requiresChoices && (request.getChoices() == null || request.getChoices().isEmpty())) {
            throw new InvalidFieldException("Choices are required for " + request.getChoiceType());
        }

        Question question = new Question();
        question.setForm(form);
        question.setName(request.getName());
        question.setChoiceType(choiceType);
        question.setIsRequired(request.getIsRequired());
        questionRepository.save(question);

        String choicesStr = null;
        if (request.getChoices() != null && !request.getChoices().isEmpty()) {
            for (String val : request.getChoices()) {
                QuestionChoice c = new QuestionChoice();
                c.setQuestion(question);
                c.setValue(val);
                choiceRepository.save(c);
            }
            choicesStr = String.join(",", request.getChoices());
        }

        return new QuestionAddResponse(
                "Add question success",
                new QuestionAddResponse.QuestionBody(
                        question.getId(),
                        question.getName(),
                        request.getChoiceType(),
                        question.getIsRequired(),
                        choicesStr,
                        form.getId()
                )
        );
    }

    @Override
    public void deleteQuestion(String slug, Long questionId, User user) {
        Form form = formRepository.findBySlugWithDomainsAndCreator(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Form not found"));

        if (!form.getCreator().getId().equals(user.getId())) {
            throw new ForbiddenException("Forbidden access");
        }

        Question question = questionRepository.findById(questionId)
                .orElseThrow(() -> new ResourceNotFoundException("Question not found"));

        if (!question.getForm().getId().equals(form.getId())) {
            throw new ResourceNotFoundException("Question not found");
        }

        questionRepository.delete(question);
    }
}