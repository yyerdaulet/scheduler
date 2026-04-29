package com.example.demo.jira.items.classrooms.dto;

import com.example.demo.jira.items.classrooms.enums.WeekDay;

import java.time.LocalTime;

public record BookClassRoomRequest(
        Long classRoomId,
        Long assignmentId,
        WeekDay day,
        LocalTime lessonStartTime
) {
}
