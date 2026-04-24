package com.example.demo.jira.items.classrooms.presentation.controller;

import com.example.demo.jira.items.classrooms.dto.ClassRoomRequest;
import com.example.demo.jira.items.classrooms.dto.ClassRoomResponse;
import com.example.demo.jira.items.classrooms.useCase.CreateClassRoom;
import com.example.demo.jira.items.classrooms.useCase.GetAllClassRooms;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@AllArgsConstructor
public class ClassRoomController {
    private final CreateClassRoom createClassRoom;
    private final GetAllClassRooms getAllClassRooms;

    @GetMapping("/classrooms")
    public ResponseEntity<List<ClassRoomResponse>> getAllCase(){
        return ResponseEntity.status(HttpStatus.OK).body(getAllClassRooms.execute());
    }

    @PostMapping("/classrooms")
    public ResponseEntity<ClassRoomResponse> createClassRoomCase(
            @RequestBody ClassRoomRequest request
            ){
        return ResponseEntity.status(HttpStatus.OK).body(createClassRoom.execute(request));
    }


}
