package com.example.demo.jira.items.classrooms.useCase;

import com.example.demo.jira.items.classrooms.domain.repository.ClassRoomRepository;
import com.example.demo.jira.items.classrooms.dto.ClassRoomResponse;
import com.example.demo.jira.items.classrooms.infrastructure.persistence.mapper.ClassRoomMapper;
import lombok.AllArgsConstructor;

import java.util.List;

@AllArgsConstructor
public class GetAllClassRooms {
    private final ClassRoomMapper classRoomMapper;
    private final ClassRoomRepository classRoomRepository;

    public List<ClassRoomResponse> execute() {
        return classRoomRepository.findAll()
                .stream()
                .map(classRoomMapper::toDto)
                .toList();
    }
}
