package com.example.demo.jira.items.assignment.dto;

public record AssignmentResponse(
        Long id,
        Long profileId,
        Long lessonId,
        Long duration
) {
}
