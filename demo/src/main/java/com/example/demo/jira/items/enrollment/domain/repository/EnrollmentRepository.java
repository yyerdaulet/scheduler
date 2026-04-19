package com.example.demo.jira.items.enrollment.domain.repository;

import com.example.demo.jira.items.enrollment.domain.model.Enrollment;

public interface EnrollmentRepository {
    Enrollment save(Enrollment enrollment);
}
