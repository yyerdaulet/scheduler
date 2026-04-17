package com.example.demo.jira.items.subject.useCase;

import com.example.demo.jira.items.subject.domain.model.Subject;
import com.example.demo.jira.items.subject.domain.repository.SubjectRepository;

import java.util.List;

public class GetSubjects {
    private final SubjectRepository repository;

    public GetSubjects(SubjectRepository repository){
        this.repository = repository;
    }

    public List<Subject> execute(){
        return repository.findAll();
    }

}
