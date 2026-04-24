package com.example.demo.jira.items.group.dto;

import com.example.demo.jira.items.group.enums.Direction;

public record GroupRequest(
        String name,
        Long enrolledYear,
        Direction direction,
        Long size
) {
}
