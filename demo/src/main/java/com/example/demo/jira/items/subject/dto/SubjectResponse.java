package com.example.demo.jira.items.subject.dto;

import com.example.demo.jira.items.group.dto.GroupResponse;
import com.example.demo.jira.items.profile.dto.ProfileResponse;
import com.example.demo.jira.items.subject.enums.ActivityType;

import java.util.List;

public record SubjectResponse(
        Long id,
        String name,
        Long credits,
        Long hours,
        ActivityType type,
        List<GroupResponse> groups,
        List<ProfileResponse> instructors
) {
}
