package com.example.demo.jira.items.subject.mapper;

import com.example.demo.jira.items.group.infrastructure.persistence.GroupMapper;
import com.example.demo.jira.items.profile.mapper.ProfileMapper;
import com.example.demo.jira.items.subject.dto.SubjectCreateRequest;
import com.example.demo.jira.items.subject.dto.SubjectResponse;
import com.example.demo.jira.items.subject.enties.SubjectEntity;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubjectMapper {
    private final GroupMapper groupMapper;
    private final ProfileMapper profileMapper;

    public SubjectEntity toEntity(SubjectCreateRequest request) {
        return new SubjectEntity(
                null,
                request.name(),
                request.credits(),
                request.hours(),
                request.type(),
                null,
                null
        );
    }

    public @NonNull SubjectResponse toDomain(SubjectEntity savedSubject) {
        return new SubjectResponse(
                savedSubject.getId(),
                savedSubject.getName(),
                savedSubject.getCredits(),
                savedSubject.getHours(),
                savedSubject.getType(),
                savedSubject.getGroups()
                        .stream().map(
                                groupMapper::toDomain
                        ).toList(),
                savedSubject.getInstructors()
                        .stream()
                        .map(profileMapper::toDomain)
                        .toList()
        );
    }
}
