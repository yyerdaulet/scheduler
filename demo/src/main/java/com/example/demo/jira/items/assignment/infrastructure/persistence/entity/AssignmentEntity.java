package com.example.demo.jira.items.assignment.infrastructure.persistence.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "assignments")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class AssignmentEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="profile_id")
    private Long profileId;

    @Column(name="lesson_id")
    private Long lessonId;

    @Column(name="isActive")
    private Boolean isActive=false;

    @Column(name="duration")
    private Long duration;
}
