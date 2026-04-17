package com.example.demo.jira.items.group.infrastructure.persistence;

import com.example.demo.jira.items.group.domain.model.Group;
import org.jspecify.annotations.NonNull;

public class GroupMapper {
    public @NonNull Group toDomain(GroupEntity savedGroup) {
        return new Group(
                savedGroup.getId(),
                savedGroup.getName(),
                savedGroup.getEnrolled_year(),
                savedGroup.getDirection()
        );
    }

    public  @NonNull GroupEntity toEntity(Group request) {
        return new GroupEntity(
                null,
                request.getName(),
                request.getEnrolled_year(),
                request.getDirection()
        );
    }
}
