package com.example.demo.jira.items.lessons.domain.model;

import com.example.demo.jira.items.subject.enums.ActivityType;
import lombok.Getter;

@Getter
public abstract class Lesson {
    private Long id;
    private String subjectName;
    private Long hours;
    private ActivityType type;

    public Lesson(Long id,String subjectName, Long hours,ActivityType type) {
        this.id = id;
        this.subjectName = subjectName;
        this.hours = hours;
        this.type = type;
    }
}
