package com.example.demo.jira.items.assigment.infrastructure.persistence.repository;

import com.example.demo.jira.items.assigment.infrastructure.persistence.entity.AssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface JpaAssignmentRepository extends JpaRepository<AssignmentEntity,Long> {
}
