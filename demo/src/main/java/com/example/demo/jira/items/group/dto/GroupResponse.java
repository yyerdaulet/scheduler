package com.example.demo.jira.items.group.dto;

import com.example.demo.jira.items.group.domain.model.Subgroup;
import com.example.demo.jira.items.group.enums.Direction;

import java.util.List;

public record GroupResponse(
        Long id,
        String name,
        Long enrolledYear,
        Direction direction,
        List<Subgroup> subgroups) {

}
