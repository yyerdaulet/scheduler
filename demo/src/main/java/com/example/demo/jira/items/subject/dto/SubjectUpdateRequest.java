package com.example.demo.jira.items.subject.dto;

import com.example.demo.jira.items.subject.enums.ActivityType;

public record SubjectUpdateRequest(
        String name,
        Long credits,
        Long hours,
        ActivityType type
) {
}
