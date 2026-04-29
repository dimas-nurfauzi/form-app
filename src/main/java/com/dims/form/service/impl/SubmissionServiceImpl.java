package com.dims.form.service.impl;

import com.dims.form.dto.answer.AnswerRequest;
import com.dims.form.dto.submission.SubmissionListResponse;
import com.dims.form.dto.submission.SubmissionRequest;
import com.dims.form.entity.*;
import com.dims.form.exception.AlreadySubmittedException;
import com.dims.form.exception.ForbiddenException;
import com.dims.form.exception.ResourceNotFoundException;
import com.dims.form.repository.*;
import com.dims.form.service.SubmissionService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional
public class SubmissionServiceImpl implements SubmissionService {

    private final FormRepository formRepository;
    private final SubmissionRepository submissionRepository;
    private final QuestionRepository questionRepository;
    private final AnswerRepository answerRepository;

    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    @Override
    public void submit(String slug, SubmissionRequest request, User user) {
        Form form = formRepository.findBySlugWithDomainsAndCreator(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Form not found"));

        validateDomain(form, user.getEmail());

        if (Boolean.TRUE.equals(form.getLimitOneResponse())) {
            boolean exists = submissionRepository
                    .existsByFormIdAndUserId(form.getId(), user.getId());
            if (exists) {
                throw new AlreadySubmittedException("You can not submit form twice");
            }
        }

        Submission submission = new Submission();
        submission.setForm(form);
        submission.setUser(user);
        submissionRepository.save(submission);

        Map<Long, Question> questionMap = questionRepository
                .findByFormId(form.getId())
                .stream()
                .collect(Collectors.toMap(Question::getId, q -> q));

        for (AnswerRequest req : request.getAnswers()) {
            Question question = questionMap.get(req.getQuestionId());
            if (question == null) {
                throw new ResourceNotFoundException("Question not found");
            }

            Answer answer = new Answer();
            answer.setSubmission(submission);
            answer.setQuestion(question);
            answer.setValue(String.valueOf(req.getValue()));
            answerRepository.save(answer);
        }
    }

    @Override
    @Transactional(readOnly = true)
    public SubmissionListResponse getSubmissions(String slug, User user) {
        Form form = formRepository.findBySlugWithDomainsAndCreator(slug)
                .orElseThrow(() -> new ResourceNotFoundException("Form not found"));

        if (!form.getCreator().getId().equals(user.getId())) {
            throw new ForbiddenException("Forbidden access");
        }

        List<SubmissionListResponse.SubmissionItem> items = submissionRepository
                .findByFormId(form.getId())
                .stream()
                .map(s -> {
                    Map<String, String> answers = new LinkedHashMap<>();
                    s.getAnswers().forEach(a ->
                            answers.put(a.getQuestion().getName(), a.getValue())
                    );
                    return new SubmissionListResponse.SubmissionItem(
                            s.getCreatedAt().format(FORMATTER),
                            new SubmissionListResponse.SubmissionItem.UserInfo(
                                    s.getUser().getId(),
                                    s.getUser().getName(),
                                    s.getUser().getEmail()
                            ),
                            answers
                    );
                })
                .toList();

        return new SubmissionListResponse("Get responses success", items);
    }

    private void validateDomain(Form form, String email) {
        List<AllowedDomain> domains = form.getAllowedDomains();
        if (domains == null || domains.isEmpty()) return;

        String userDomain = email.substring(email.indexOf("@") + 1);
        boolean allowed = domains.stream()
                .anyMatch(d -> d.getDomain().equalsIgnoreCase(userDomain));

        if (!allowed) {
            throw new ForbiddenException("Forbidden access");
        }
    }
}