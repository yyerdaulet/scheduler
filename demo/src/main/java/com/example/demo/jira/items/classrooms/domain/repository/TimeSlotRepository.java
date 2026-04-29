package com.example.demo.jira.items.classrooms.domain.repository;

import com.example.demo.jira.items.classrooms.domain.model.Timeslot;

import java.util.List;


public interface TimeSlotRepository {
    Timeslot save(Timeslot timeslot);

    List<Timeslot> saveAll(List<Timeslot> timeslots);
}
