package com.example.demo.jira.items.assignment.infrastructure.persistence.mapper;

import com.example.demo.jira.items.assignment.domain.model.Assignment;
import com.example.demo.jira.items.assignment.dto.AssignmentResponse;
import com.example.demo.jira.items.assignment.infrastructure.persistence.entity.AssignmentEntity;
import org.springframework.stereotype.Component;

@Component
public class AssignmentMapper {
    public AssignmentResponse toDto(Assignment saved) {
        return new AssignmentResponse(
                saved.getId(),
                saved.getProfileId(),
                saved.getLessonId(),
                saved.getDuration()
        );
    }

    public AssignmentEntity toEntity(Assignment assignment) {
        return new AssignmentEntity(
                assignment.getId(),
                assignment.getProfileId(),
                assignment.getLessonId(),
                assignment.getIsActive(),
                assignment.getDuration()
        );
    }

    public Assignment toDomain(AssignmentEntity savedAssignmentEntity) {
        return new Assignment(
                savedAssignmentEntity.getId(),
                savedAssignmentEntity.getProfileId(),
                savedAssignmentEntity.getLessonId(),
                savedAssignmentEntity.getIsActive(),
                savedAssignmentEntity.getDuration()
        );
    }
}
