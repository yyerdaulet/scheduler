package com.example.demo.jira.items.subject.controller;


import com.example.demo.jira.items.subject.dto.SubjectCreateRequest;
import com.example.demo.jira.items.subject.dto.SubjectResponse;
import com.example.demo.jira.items.subject.dto.SubjectUpdateRequest;
import com.example.demo.jira.items.subject.services.SubjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubjectController {
    private final SubjectService subjectService;

    @GetMapping("/subjects")
    public ResponseEntity<List<SubjectResponse>> getAllSubjects() {
        return ResponseEntity.status(HttpStatus.OK).body(subjectService.getAllSubjects());
    }

    @GetMapping("/subjects/{subjectId}")
    public ResponseEntity<SubjectResponse> getSubject(
            @PathVariable Long subjectId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(subjectService.getSubject(subjectId));
    }

    @PostMapping("/subjects")
    public ResponseEntity<SubjectResponse> createSubject(
            @RequestBody SubjectCreateRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(subjectService.createSubject(request));
    }

    @PutMapping("/subjects/{subjectId}")
    public ResponseEntity<SubjectResponse> updateSubject(
            @RequestBody SubjectUpdateRequest request,
            @PathVariable Long subjectId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(subjectService.updateSubject(request, subjectId));
    }

    @DeleteMapping("/subjects/{subjectId}")
    public ResponseEntity<Void> deleteSubject(
            @PathVariable Long subjectId
    ) {
        subjectService.deleteSubject(subjectId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
