package com.example.demo.jira.items.classrooms.domain.model;


import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ClassRoom {
    private Long id;
    private Long number;
    private Long size;
    private List<Timeslot> timeslots;
}
