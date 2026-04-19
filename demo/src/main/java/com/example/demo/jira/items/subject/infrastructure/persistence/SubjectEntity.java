package com.example.demo.jira.items.subject.infrastructure.persistence;

import com.example.demo.jira.items.group.infrastructure.persistence.GroupEntity;
import com.example.demo.jira.items.profile.infrastucture.persistence.ProfileEntity;
import com.example.demo.jira.items.subject.enums.ActivityType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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
}
