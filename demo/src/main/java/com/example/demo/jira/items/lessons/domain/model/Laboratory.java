package com.example.demo.jira.items.lessons.domain.model;

import com.example.demo.jira.items.subject.enums.ActivityType;
import lombok.Getter;

@Getter
public class Laboratory extends Lesson{
    private final Long groupId;
    private final Long subgroupId;

    public Laboratory(Long id,String subjectName, Long hours, ActivityType type, Long groupId, Long subgroupId) {
        super(id,subjectName,hours,type);
        this.groupId = groupId;
        this.subgroupId = subgroupId;
    }
}
