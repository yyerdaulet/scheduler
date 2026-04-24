package com.example.demo.jira.items.lessons.infrastructure.persistence.entity;

import com.example.demo.jira.items.subject.enums.ActivityType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@DiscriminatorValue("LECTURE")
@Getter
public class LectureEntity extends LessonEntity{
    @Column(name = "groups")
    private List<Long> groupsId;

    public LectureEntity(Long id, String subjectName,Long hours, List<Long> groupsId) {
        super(id, subjectName,hours);
        this.groupsId = groupsId;
    }


    @Override
    public ActivityType getType() {
        return ActivityType.LECTURE;
    }
}
