package com.example.demo.jira.items.enrollment.dto;

import java.util.List;

public record EnrollmentResponse(
        Long id,
        Long subjectId,
        List<Long> groupId
) {
}
