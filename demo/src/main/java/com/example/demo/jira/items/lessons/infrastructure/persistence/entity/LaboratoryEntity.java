package com.example.demo.jira.items.lessons.infrastructure.persistence.entity;

import com.example.demo.jira.items.subject.enums.ActivityType;
import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import lombok.Getter;
import lombok.Setter;

@Entity
@DiscriminatorValue("LABORATORY")
@Getter
public class LaboratoryEntity extends LessonEntity{
    @Column(name="groupId")
    private  Long groupId;

    @Column(name="subgroupId")
    private  Long subgroupId;

    public LaboratoryEntity(Long id,String subjectName, Long hours, Long groupId, Long subgroupId) {
        super(id, subjectName,hours);
        this.groupId = groupId;
        this.subgroupId = subgroupId;
    }

    public LaboratoryEntity(){

    }

    @Override
    public ActivityType getType() {
        return ActivityType.LABORATORY;
    }
}
