package com.example.demo.jira.items.subject.presentation.controller;

import com.example.demo.jira.items.subject.domain.model.Subject;
import com.example.demo.jira.items.subject.dto.SubjectResponse;
import com.example.demo.jira.items.subject.dto.SubjectRequest;
import com.example.demo.jira.items.subject.useCase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class SubjectController {
    private final GetSubjects getSubjects;
    private final GetSubject getSubject;
    private final CreateSubject createSubject;
    private final UpdateSubject updateSubject;
    private final DeleteSubject deleteSubject;


    @GetMapping("/subjects")
    public ResponseEntity<List<Subject>> getAllSubjects() {
        return ResponseEntity.status(HttpStatus.OK).body(getSubjects.execute());
    }

    @GetMapping("/subjects/{subjectId}")
    public ResponseEntity<Subject> getSubject(
            @PathVariable Long subjectId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(getSubject.execute(subjectId));
    }

    @PostMapping("/subjects")
    public ResponseEntity<SubjectResponse> createSubject(
            @RequestBody SubjectRequest request
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(createSubject.execute(request));
    }

    @PutMapping("/subjects/{subjectId}")
    public ResponseEntity<Subject> updateSubject(
            @RequestBody Subject request,
            @PathVariable Long subjectId
    ) {
        return ResponseEntity.status(HttpStatus.OK).body(updateSubject.execute(subjectId,request));
    }

    @DeleteMapping("/subjects/{subjectId}")
    public ResponseEntity<Void> deleteSubject(
            @PathVariable Long subjectId
    ) {
        deleteSubject.execute(subjectId);
        return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
    }
}
