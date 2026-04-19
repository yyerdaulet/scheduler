package com.example.demo.jira.items.enrollment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Enrollment {
    private Long id;
    private Long subjectId;
    private Long groupId;
}
