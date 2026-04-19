package com.example.demo.jira.items.subject.domain.model;

import com.example.demo.jira.items.subject.enums.ActivityType;
import lombok.Getter;

@Getter
public class Subject {
    private final Long id;
    private final String name;
    private final Long credits;

    public Subject(Long id, String name, Long credits) {
        validateCredits(credits);

        this.id = id;
        this.name = name;
        this.credits = credits;
    }

    public void validateCredits(Long credits){
        if(credits > 8){
            throw new IllegalArgumentException("Too many credits");
        }
    }





}
