package com.example.demo.jira.items.classrooms.domain.model;

import com.example.demo.jira.items.classrooms.enums.RoomState;
import com.example.demo.jira.items.classrooms.enums.WeekDay;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Timeslot {
    private Long id;
    private LocalTime start;
    private LocalTime finish;
    private RoomState roomState;
    private WeekDay weekday;
    private Long bookedByAssignmentId;
}
