package com.example.demo.jira.items.subject.dto;

import com.example.demo.jira.items.subject.enums.ActivityType;

public record SubjectRequest(
        String name,
        Long credits
) {
}
