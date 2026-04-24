package com.example.demo.jira.items.lessons.infrastructure.persistence.entity;

import com.example.demo.jira.items.subject.enums.ActivityType;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;

@Entity
@DiscriminatorValue("SEMINAR")
@Getter
public class SeminarEntity extends LessonEntity{
    private Long groupId;

    public SeminarEntity(Long id, String subjectName,Long hours, Long groupId) {
        super(id,subjectName,hours);
        this.groupId = groupId;
    }

    public SeminarEntity(){

    }

    @Override
    public ActivityType getType() {
        return ActivityType.SEMINAR;
    }
}
