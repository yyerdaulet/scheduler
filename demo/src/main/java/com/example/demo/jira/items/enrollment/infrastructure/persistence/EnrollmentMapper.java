package com.example.demo.jira.items.enrollment.infrastructure.persistence;

import com.example.demo.jira.items.enrollment.domain.model.Enrollment;
import com.example.demo.jira.items.enrollment.dto.EnrollmentResponse;
import org.springframework.stereotype.Component;

@Component
public class EnrollmentMapper {
    public Enrollment toDomain(EnrollmentEntity entity) {
        return new Enrollment(
                entity.getId(),
                entity.getSubjectId(),
                entity.getGroupsId()
        );
    }

    public EnrollmentEntity toEntity(Enrollment enrollment){
        return new EnrollmentEntity(
                enrollment.getId(),
                enrollment.getSubjectId(),
                enrollment.getGroupsId()
        );
    }

    public EnrollmentResponse toDto(Enrollment enrollment) {
        return new EnrollmentResponse(
                enrollment.getId(),
                enrollment.getSubjectId(),
                enrollment.getGroupsId()
        );
    }
}
