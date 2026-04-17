package com.example.demo.jira.items.subject.useCase;

import com.example.demo.jira.items.subject.domain.repository.SubjectRepository;
import jakarta.persistence.EntityNotFoundException;

public class DeleteSubject {
    private final SubjectRepository repository;

    public DeleteSubject(SubjectRepository repository){
        this.repository = repository;
    }

    public void execute(Long subjectId){
        validate(subjectId);
        repository.delete(subjectId);
    }

    private void validate(Long subjectId){
        if(!repository.existById(subjectId)){
            throw new EntityNotFoundException(
                    "Subject Not Found : " + subjectId
            );
        }
    }
}
