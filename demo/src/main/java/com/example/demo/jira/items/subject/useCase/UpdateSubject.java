package com.example.demo.jira.items.subject.useCase;

import com.example.demo.jira.items.subject.domain.model.Subject;
import com.example.demo.jira.items.subject.domain.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;

public class UpdateSubject {
    private final SubjectRepository repository;

    public UpdateSubject(SubjectRepository repository){
        this.repository = repository;
    }

    public Subject execute(Long subjectId,Subject request){
        Subject subject = repository.findById(subjectId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Subject Not Found : " + subjectId)
                );
        subject.update(
                request.getName(),
                request.getCredits(),
                request.getHours(),
                request.getType()
        );

        return repository.save(subject);
    }
}
