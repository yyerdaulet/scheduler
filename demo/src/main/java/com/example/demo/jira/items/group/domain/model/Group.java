package com.example.demo.jira.items.group.domain.model;

import com.example.demo.jira.items.group.enums.Direction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@AllArgsConstructor
@Getter
@Setter
public class Group {
    private Long id;
    private String name;
    private Long enrolled_year;
    private Direction direction;

}
