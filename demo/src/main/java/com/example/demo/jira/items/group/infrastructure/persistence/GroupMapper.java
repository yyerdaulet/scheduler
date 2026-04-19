package com.example.demo.jira.items.group.infrastructure.persistence;

import com.example.demo.jira.items.group.domain.model.Group;
import com.example.demo.jira.items.group.dto.GroupResponse;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

@Component
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
                request.getEnrolledYear(),
                request.getDirection()
        );
    }

    public GroupResponse toDto(Group group) {
        return new GroupResponse(
            group.getId(),
                group.getName(),
                group.getEnrolledYear(),
                group.getDirection()
        );
    }
}
