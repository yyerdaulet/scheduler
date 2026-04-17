package com.example.demo.jira.items.subject.domain.model;

import com.example.demo.jira.items.subject.enums.ActivityType;
import lombok.Getter;

@Getter
public class Subject {
    private Long id;
    private String name;
    private Long credits;
    private Long hours;
    private ActivityType type;

    public Subject(Long id, String name, Long credits, Long hours, ActivityType type) {
    }

    public void update(String name,Long credits,Long hours,ActivityType type) {
        this.name = name;
        this.credits = credits;
        this.hours = hours;
        this.type = type;
    }
}
