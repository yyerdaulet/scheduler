package com.example.demo.jira.items.assigment.domain.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class Assignment {
    private Long id;
    private Long profileId;
    private Long lessonId;
}
