package com.example.demo.jira.items.classrooms.dto;

import com.example.demo.jira.items.classrooms.domain.model.Timeslot;

import java.util.List;

public record ClassRoomResponse(
        Long id,
        Long number,
        Long size,
        List<Timeslot> timeslots) {
}
