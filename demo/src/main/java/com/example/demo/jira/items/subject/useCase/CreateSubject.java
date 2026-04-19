package com.example.demo.jira.items.subject.useCase;

import com.example.demo.jira.items.subject.domain.model.Subject;
import com.example.demo.jira.items.subject.domain.repository.SubjectRepository;
import com.example.demo.jira.items.subject.dto.SubjectResponse;
import com.example.demo.jira.items.subject.dto.SubjectRequest;

public class CreateSubject {
    private final SubjectRepository repository;

    public CreateSubject(SubjectRepository repository){
        this.repository = repository;
    }

    public SubjectResponse execute(SubjectRequest request){

         Subject subject = new Subject(
                null,
                    request.name(),
                 request.credits()
         );
         Subject savedSubject = repository.save(subject);

         return new SubjectResponse(
                 savedSubject.getId(),
                 savedSubject.getName(),
                 savedSubject.getCredits()
         );
    }
}
