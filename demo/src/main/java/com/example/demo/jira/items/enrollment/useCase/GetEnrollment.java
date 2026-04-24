package com.example.demo.jira.items.enrollment.useCase;

import com.example.demo.jira.items.enrollment.domain.model.Enrollment;
import com.example.demo.jira.items.enrollment.domain.repository.EnrollmentRepository;
import com.example.demo.jira.items.enrollment.dto.EnrollmentResponse;
import com.example.demo.jira.items.enrollment.infrastructure.persistence.EnrollmentMapper;
import jakarta.persistence.EntityNotFoundException;

public class GetEnrollment {
    private final EnrollmentRepository enrollmentRepository;
    private final EnrollmentMapper enrollmentMapper;

    public GetEnrollment(EnrollmentRepository repository,EnrollmentMapper mapper){
        this.enrollmentRepository = repository;
        this.enrollmentMapper = mapper;
    }

    public EnrollmentResponse execute(Long id){
        Enrollment enrollment = findEnrollmentById(id);

        return enrollmentMapper.toDto(enrollment);
    }

    private Enrollment findEnrollmentById(Long id) {
        return enrollmentRepository.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Enrollment Not Found : " + id)
                );
    }
}
