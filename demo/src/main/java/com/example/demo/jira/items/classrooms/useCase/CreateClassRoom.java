package com.example.demo.jira.items.classrooms.useCase;

import com.example.demo.jira.items.classrooms.domain.model.ClassRoom;
import com.example.demo.jira.items.classrooms.domain.repository.ClassRoomRepository;
import com.example.demo.jira.items.classrooms.dto.ClassRoomRequest;
import com.example.demo.jira.items.classrooms.dto.ClassRoomResponse;
import com.example.demo.jira.items.classrooms.infrastructure.persistence.mapper.ClassRoomMapper;
import lombok.AllArgsConstructor;
import org.jspecify.annotations.NonNull;

@AllArgsConstructor
public class CreateClassRoom {
    private final ClassRoomRepository classRoomRepository;
    private final ClassRoomMapper classRoomMapper;

    public ClassRoomResponse execute(ClassRoomRequest request) {
        ClassRoom classRoom = buildClassRoom(request);

        ClassRoom savedClassRoom = classRoomRepository.save(classRoom);

        return classRoomMapper.toDto(savedClassRoom);
    }

    private static @NonNull ClassRoom buildClassRoom(ClassRoomRequest request) {
        return new ClassRoom(
                null,
                request.number(),
                request.size()
        );
    }
}
