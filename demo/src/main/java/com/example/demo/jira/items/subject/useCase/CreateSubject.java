package com.example.demo.jira.items.subject.useCase;

import com.example.demo.jira.items.subject.domain.model.Subject;
import com.example.demo.jira.items.subject.domain.repository.SubjectRepository;

public class CreateSubject {
    private final SubjectRepository repository;

    public CreateSubject(SubjectRepository repository){
        this.repository = repository;
    }

    public Subject execute(Subject subject){
         return repository.save(subject);
    }
}
