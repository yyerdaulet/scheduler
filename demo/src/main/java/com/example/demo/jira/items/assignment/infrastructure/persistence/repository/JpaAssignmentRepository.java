package com.example.demo.jira.items.assignment.infrastructure.persistence.repository;

import com.example.demo.jira.items.assignment.infrastructure.persistence.entity.AssignmentEntity;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface JpaAssignmentRepository extends JpaRepository<AssignmentEntity,Long> {
    Boolean existsByLessonId(Long lessonId);
    List<AssignmentEntity> findAllByIsActiveFalse();
}
