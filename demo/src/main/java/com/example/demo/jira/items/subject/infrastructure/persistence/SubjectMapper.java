package com.example.demo.jira.items.subject.infrastructure.persistence;

import com.example.demo.jira.items.group.infrastructure.persistence.GroupMapper;
import com.example.demo.jira.items.profile.infrastucture.persistence.ProfileMapper;
import com.example.demo.jira.items.subject.domain.model.Subject;
import lombok.RequiredArgsConstructor;
import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class SubjectMapper {

    public SubjectEntity toEntity(Subject request) {
        return new SubjectEntity(
                null,
                request.getName(),
                request.getCredits(),
                request.getHours(),
                request.getType()
        );
    }

    public @NonNull Subject toDomain(SubjectEntity savedSubject) {
        return new Subject(
                savedSubject.getId(),
                savedSubject.getName(),
                savedSubject.getCredits(),
                savedSubject.getHours(),
                savedSubject.getType()
        );
    }
}
