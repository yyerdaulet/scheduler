package com.example.demo.jira.items.subject.enties;

import com.example.demo.jira.items.group.infrastructure.persistence.GroupEntity;
import com.example.demo.jira.items.profile.model.ProfileEntity;
import com.example.demo.jira.items.subject.enums.ActivityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name="subjects")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SubjectEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="name")
    private String name;

    @Column(name="credits")
    private Long credits;

    @Column(name="hours")
    private Long hours;

    @Column(name="type")
    private ActivityType type;

    @ManyToMany
    private List<ProfileEntity> instructors;

    @Column(name="groups")
    private List<GroupEntity> groups;
}
