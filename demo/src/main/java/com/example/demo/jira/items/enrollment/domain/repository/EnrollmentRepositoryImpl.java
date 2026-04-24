package com.example.demo.jira.items.enrollment.domain.repository;

import com.example.demo.jira.items.enrollment.domain.model.Enrollment;
import com.example.demo.jira.items.enrollment.infrastructure.persistence.EnrollmentEntity;
import com.example.demo.jira.items.enrollment.infrastructure.persistence.EnrollmentMapper;
import com.example.demo.jira.items.enrollment.infrastructure.persistence.JpaEnrollmentRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
@AllArgsConstructor
public class EnrollmentRepositoryImpl implements EnrollmentRepository{
    private JpaEnrollmentRepository jpa;
    private EnrollmentMapper mapper;

    @Override
    public Enrollment save(Enrollment enrollment) {
        return mapper.toDomain(
                jpa.save(mapper.toEntity(enrollment))
        );
    }

    @Override
    public List<Enrollment> findAll() {
        return jpa.findAll()
                .stream().map(
                        mapper::toDomain
                ).toList();
    }

    @Override
    public Optional<Enrollment> findById(Long id) {
        EnrollmentEntity entity =  jpa.findById(id)
                .orElseThrow(
                        () -> new EntityNotFoundException("Enrollment not found : " + id)
                );
        return Optional.ofNullable(mapper.toDomain(entity));
    }
}

