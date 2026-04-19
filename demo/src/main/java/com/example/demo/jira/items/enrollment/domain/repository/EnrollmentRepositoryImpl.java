package com.example.demo.jira.items.enrollment.domain.repository;

import com.example.demo.jira.items.enrollment.domain.model.Enrollment;
import com.example.demo.jira.items.enrollment.infrastructure.persistence.EnrollmentMapper;
import com.example.demo.jira.items.enrollment.infrastructure.persistence.JpaEnrollmentRepository;
import org.springframework.stereotype.Repository;

@Repository
public class EnrollmentRepositoryImpl implements EnrollmentRepository{
    private JpaEnrollmentRepository repository;
    private EnrollmentMapper mapper;

    @Override
    public Enrollment save(Enrollment enrollment) {
        return mapper.toDomain(
                repository.save(mapper.toEntity(enrollment))
        );
    }
}
