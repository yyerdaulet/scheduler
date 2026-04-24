package com.example.demo.jira.items.lessons.dto;

import com.example.demo.jira.items.subject.enums.ActivityType;

import java.util.List;

public record LessonResponse(
        Long id,
        String subjectName, Long hours,
        ActivityType type,
        List<Long> groupsId,
        Long groupId,
        Long subgroupId
) {
}
