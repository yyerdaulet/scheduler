package com.example.demo.jira.items.subject.domain.repository;

import com.example.demo.jira.items.subject.domain.model.Subject;
import java.util.List;
import java.util.Optional;

public interface SubjectRepository {
    List<Subject> findAll();
    Optional<Subject> findById(Long subjectId);
    Subject save(Subject subject);
    void delete(Long subjectId);
    Boolean existById(Long subjectId);
}
