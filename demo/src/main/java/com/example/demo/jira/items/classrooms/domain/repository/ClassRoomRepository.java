package com.example.demo.jira.items.classrooms.domain.repository;

import com.example.demo.jira.items.classrooms.domain.model.ClassRoom;

import java.util.Collection;
import java.util.List;

public interface ClassRoomRepository {
    ClassRoom save(ClassRoom classRoom);

    List<ClassRoom> findAll();
}
