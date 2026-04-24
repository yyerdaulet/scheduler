package com.example.demo.jira.items.classrooms.infrastructure.persistence.mapper;

import com.example.demo.jira.items.classrooms.domain.model.ClassRoom;
import com.example.demo.jira.items.classrooms.dto.ClassRoomResponse;
import com.example.demo.jira.items.classrooms.infrastructure.persistence.entity.ClassRoomEntity;
import org.springframework.stereotype.Component;

@Component
public class ClassRoomMapper {
    public ClassRoomEntity toEntity(ClassRoom classRoom) {
        return new ClassRoomEntity(
            null,
            classRoom.getNumber(),
            classRoom.getSize()
        );

    }

    public ClassRoom toDomain(ClassRoomEntity saved) {
        return new ClassRoom(
                saved.getId(),
                saved.getNumber(),
                saved.getSize()
        );
    }

    public ClassRoomResponse toDto(ClassRoom savedClassRoom) {
        return new ClassRoomResponse(
                savedClassRoom.getId(),
                savedClassRoom.getNumber(),
                savedClassRoom.getSize()
        );
    }
}
