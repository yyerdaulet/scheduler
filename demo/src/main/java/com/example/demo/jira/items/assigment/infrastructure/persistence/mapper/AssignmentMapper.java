package com.example.demo.jira.items.assigment.infrastructure.persistence.mapper;

import com.example.demo.jira.items.assigment.domain.model.Assignment;
import com.example.demo.jira.items.assigment.dto.AssignmentResponse;
import com.example.demo.jira.items.assigment.infrastructure.persistence.entity.AssignmentEntity;
import org.springframework.stereotype.Component;

@Component
public class AssignmentMapper {
    public AssignmentResponse toDto(Assignment saved) {
        return new AssignmentResponse(
                saved.getId(),
                saved.getProfileId(),
                saved.getLessonId()
        );
    }

    public AssignmentEntity toEntity(Assignment assignment) {
        return new AssignmentEntity(
                null,
                assignment.getProfileId(),
                assignment.getLessonId()
        );
    }

    public Assignment toDomain(AssignmentEntity savedAssignmentEntity) {
        return new Assignment(
                savedAssignmentEntity.getId(),
                savedAssignmentEntity.getProfileId(),
                savedAssignmentEntity.getLessonId()
        );
    }
}
