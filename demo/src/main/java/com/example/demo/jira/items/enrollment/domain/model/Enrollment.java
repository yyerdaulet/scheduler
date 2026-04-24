package com.example.demo.jira.items.enrollment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.util.List;

@Getter
@AllArgsConstructor
public class Enrollment {
    private Long id;
    private Long subjectId;
    private List<Long> groupsId;
}
