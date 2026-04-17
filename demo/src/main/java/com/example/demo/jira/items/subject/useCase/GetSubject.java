package com.example.demo.jira.items.subject.useCase;

import com.example.demo.jira.items.subject.domain.model.Subject;
import com.example.demo.jira.items.subject.domain.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;

public class GetSubject {
    private final SubjectRepository repository;

    public GetSubject(SubjectRepository repository){
        this.repository = repository;
    }

    public Subject execute(Long subjectId){
        return repository.findById(subjectId).orElseThrow(
                () -> new EntityNotFoundException("Subject Not Found")
        );
    }
}
