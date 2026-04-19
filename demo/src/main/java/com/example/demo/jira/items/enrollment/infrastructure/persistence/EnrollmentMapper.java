package com.example.demo.jira.items.enrollment.infrastructure.persistence;

import com.example.demo.jira.items.enrollment.domain.model.Enrollment;

public class EnrollmentMapper {
    public Enrollment toDomain(EnrollmentEntity entity) {
        return new Enrollment(
                entity.getId(),
                entity.getSubjectId(),
                entity.getGroupId()
        );
    }

    public EnrollmentEntity toEntity(Enrollment enrollment){
        return new EnrollmentEntity(
                enrollment.getId(),
                enrollment.getSubjectId(),
                enrollment.getGroupId()
        );
    }
}
