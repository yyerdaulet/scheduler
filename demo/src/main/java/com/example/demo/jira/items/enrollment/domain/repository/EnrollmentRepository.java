package com.example.demo.jira.items.enrollment.domain.repository;

import com.example.demo.jira.items.enrollment.domain.model.Enrollment;

import java.util.List;
import java.util.Optional;

public interface EnrollmentRepository {
    Enrollment save(Enrollment enrollment);
    List<Enrollment> findAll();
    Optional<Enrollment> findById(Long id);
}
