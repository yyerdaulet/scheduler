package com.example.demo.jira.items.classrooms.domain.repository;

import com.example.demo.jira.items.classrooms.domain.model.ClassRoom;

import java.util.List;
import java.util.Optional;

public interface ClassRoomRepository {
    ClassRoom save(ClassRoom classRoom);

    List<ClassRoom> findAll();

    Optional<ClassRoom> findById(Long aLong);

    Boolean existById(Long classRoomId);

    void delete(Long classRoomId);
}
