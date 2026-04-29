package com.example.demo.jira.items.classrooms.presentation.controller;

import com.example.demo.jira.items.classrooms.dto.BookClassRoomRequest;
import com.example.demo.jira.items.classrooms.dto.ClassRoomRequest;
import com.example.demo.jira.items.classrooms.dto.ClassRoomResponse;
import com.example.demo.jira.items.classrooms.dto.ClassRoomUpdateRequest;
import com.example.demo.jira.items.classrooms.useCase.*;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@AllArgsConstructor
public class ClassRoomController {
    private final CreateClassRoom createClassRoom;
    private final GetAllClassRooms getAllClassRooms;
    private final BookClassRoom bookClassRoom;
    private final DeleteClassRoom deleteClassRoom;
    private final UpdateClassRoom updateClassRoom;

    @GetMapping("/classrooms")
    public ResponseEntity<List<ClassRoomResponse>> getAllCase(){
        return ResponseEntity.status(HttpStatus.OK).body(getAllClassRooms.execute());
    }

    @DeleteMapping("/classrooms/{classroomId}")
    public ResponseEntity<List<ClassRoomResponse>> deleteById(
            @PathVariable Long classroomId
    ){
        deleteClassRoom.execute(classroomId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }

    @PostMapping("/classrooms")
    public ResponseEntity<ClassRoomResponse> createClassRoomCase(
            @RequestBody ClassRoomRequest request
            ){
        return ResponseEntity.status(HttpStatus.OK).body(createClassRoom.execute(request));
    }

    @PostMapping("/classrooms/book")
    public ResponseEntity<ClassRoomResponse> bookClassRoomCase(
            @RequestBody BookClassRoomRequest request
            ){
        return ResponseEntity.status(HttpStatus.OK).body(bookClassRoom.execute(request));
    }

    @PutMapping("/classrooms/{classroomId}")
    public ResponseEntity<ClassRoomResponse> updateClassRoomCase(
            @PathVariable Long classroomId,
            @RequestBody ClassRoomUpdateRequest request
    ){
        return ResponseEntity.status(HttpStatus.OK).body(updateClassRoom.execute(classroomId,request));
    }


}
