package com.example.demo.jira.items.enrollment.infrastructure.persistence;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JpaEnrollmentRepository extends JpaRepository<EnrollmentEntity,Long> {
}
