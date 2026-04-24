package com.example.demo.jira.items.enrollment.dto;

import java.util.List;

public record EnrollmentRequest(
        Long subjectId,
        List<Long> groupsId
) {
}
