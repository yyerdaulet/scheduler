package com.example.demo.jira.items.lessons.domain.model;

import com.example.demo.jira.items.subject.enums.ActivityType;
import lombok.Getter;

@Getter
public class Seminar extends Lesson{
    private final Long groupId;

    public Seminar(Long id,String subjectName, Long hours,ActivityType type,Long groupId) {
        super(id, subjectName,hours,type);
        this.groupId = groupId;
    }
}
