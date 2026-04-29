package com.example.demo.jira.items.assignment.domain.repository;

import com.example.demo.jira.items.assignment.domain.model.Assignment;

import java.util.List;
import java.util.Optional;

public interface AssignmentRepository {
    Assignment save(Assignment assignment);

    List<Assignment> findAll();

    Optional<Assignment> findById(Long id);

    boolean existByLessonId(Long lessonId);

    List<Assignment> findAllNonActive();
}
