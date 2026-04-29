package com.example.demo.jira.items.classrooms.useCase;


import com.example.demo.jira.items.classrooms.domain.model.ClassRoom;
import com.example.demo.jira.items.classrooms.domain.repository.ClassRoomRepository;
import com.example.demo.jira.items.classrooms.dto.ClassRoomResponse;
import com.example.demo.jira.items.classrooms.dto.ClassRoomUpdateRequest;
import com.example.demo.jira.items.classrooms.infrastructure.persistence.mapper.ClassRoomMapper;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class UpdateClassRoom {
    private ClassRoomRepository classRoomRepository;
    private ClassRoomMapper classRoomMapper;

    public ClassRoomResponse execute(
            Long classRoomId,
            ClassRoomUpdateRequest request
    ){
        ClassRoom classRoom = findClassRoomById(classRoomId);
        classRoom.setSize(request.size());
        classRoom.setNumber(request.number());

        ClassRoom savedClassRoom = classRoomRepository.save(classRoom);

        return classRoomMapper.toDto(savedClassRoom);
    }

    private ClassRoom findClassRoomById(Long classRoomId) {
        return classRoomRepository
                .findById(classRoomId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Class Room Not Found")
                );
    }
}
