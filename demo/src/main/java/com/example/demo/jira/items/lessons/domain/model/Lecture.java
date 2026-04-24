package com.example.demo.jira.items.lessons.domain.model;

import com.example.demo.jira.items.subject.enums.ActivityType;
import lombok.Getter;

import java.util.List;

@Getter
public class Lecture extends Lesson{
    private final List<Long> groupsId;

    public Lecture(Long id,String subjectName, Long hours, ActivityType type, List<Long> groupsId) {
        super(id,subjectName, hours,type);
        this.groupsId = groupsId;
    }


}
