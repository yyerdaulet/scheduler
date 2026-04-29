package com.example.demo.jira.items.classrooms.useCase;

import com.example.demo.jira.items.classrooms.domain.repository.ClassRoomRepository;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class DeleteClassRoom {
    private ClassRoomRepository classRoomRepository;

    public void execute(Long classRoomId){
        existByClassRoomId(classRoomId);
        classRoomRepository.delete(classRoomId);
    }

    private void existByClassRoomId(Long classRoomId) {
        if(!classRoomRepository.existById(classRoomId)){
            throw new EntityNotFoundException("ClassRoom Not Found");
        }
    }

}
