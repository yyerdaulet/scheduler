package com.example.demo.jira.items.classrooms.infrastructure.persistence.entity;

import com.example.demo.jira.items.classrooms.domain.model.Timeslot;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table
public class ClassRoomEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="number")
    private Long number;

    @Column(name="size")
    private Long size;

    @OneToMany(mappedBy = "classRoom",cascade = CascadeType.ALL,orphanRemoval = true)
    private List<TimeSlotEntity> timeslot = new ArrayList<>();

}
