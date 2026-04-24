package com.example.demo.jira.items.lessons.presentation.controller;

import com.example.demo.jira.items.lessons.dto.LessonRequest;
import com.example.demo.jira.items.lessons.dto.LessonResponse;
import com.example.demo.jira.items.lessons.useCases.CreateLessons;
import com.example.demo.jira.items.lessons.useCases.GetAllLessons;
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
public class LessonController {
    private final CreateLessons createLessons;
    private final GetAllLessons getAllLessons;

    @GetMapping("/lessons")
    public ResponseEntity<List<LessonResponse>> getAll(){
        return ResponseEntity.status(HttpStatus.OK).body(getAllLessons.execute());
    }

    @PostMapping("/lessons")
    public ResponseEntity<List<LessonResponse>> createGroupLessons(
            @RequestBody LessonRequest request
            ){
        return ResponseEntity.status(HttpStatus.OK).body(createLessons.execute(request));
    }
}
