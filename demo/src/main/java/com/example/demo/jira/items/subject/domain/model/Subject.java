package com.example.demo.jira.items.subject.domain.model;

import com.example.demo.jira.items.subject.enums.ActivityType;
import lombok.Getter;

@Getter
public class Subject {
    private final Long id;
    private final String name;
    private final Long credits;
    private final Long hours;
    private final ActivityType type;

    public Subject(Long id, String name, Long credits, ActivityType type,Long hours) {
        validateCredits(credits);

        this.id = id;
        this.name = name;
        this.credits = credits;
        this.hours = hours;
        this.type = type;
    }

    public void validateCredits(Long credits){
        if(credits > 8){
            throw new IllegalArgumentException("Too many credits");
        }
    }





}
