package com.example.demo.jira.items.assignment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Assignment {
    private Long id;
    private Long profileId;
    private Long lessonId;
    private Boolean isActive;
    private Long duration;
}
